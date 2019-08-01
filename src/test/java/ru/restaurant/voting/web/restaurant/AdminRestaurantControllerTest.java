package ru.restaurant.voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.service.restaurant.RestaurantService;
import ru.restaurant.voting.util.exception.NotFoundException;
import ru.restaurant.voting.web.AbstractControllerTest;
import ru.restaurant.voting.web.ExceptionInfoHandler;
import ru.restaurant.voting.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.voting.RestaurantTestData.*;
import static ru.restaurant.voting.TestUtil.*;
import static ru.restaurant.voting.UserTestData.ADMIN;
import static ru.restaurant.voting.UserTestData.USER5;
import static ru.restaurant.voting.util.exception.ErrorType.DATA_NOT_FOUND;
import static ru.restaurant.voting.util.exception.ErrorType.VALIDATION_ERROR;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    private static final String REST_URL = AdminRestaurantController.REST_URL + '/';

    @Test
    void testCreate() throws Exception {
        Restaurant created = new Restaurant(null, "One more Restaurant");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated())
                .andDo(print());

        Restaurant returned = readFromJson(action, Restaurant.class);
        created.setId(returned.getId());
        assertMatch(created, returned);

        Restaurant saved = restaurantService.get(returned.getId());
        assertMatch(returned, saved);
    }

    @Test
    void createHtmlUnsafe() throws Exception {
        Restaurant invalid = new Restaurant(null, "<script>alert(123)</script>");

        mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(RES4_ID, "new Name");

        mockMvc.perform(put(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(restaurantService.get(RES4_ID), updated);
    }

    @Test
    void testUpdateHtmlUnsafe() throws Exception {
        Restaurant invalid = new Restaurant(RES7_ID, "<script>alert(123)</script>");

        mockMvc.perform(put(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RES1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(restaurantService.getAll(), RES2, RES3, RES4, RES5, RES6, RES7, RES8, RES9);
        assertThrows(NotFoundException.class, () -> restaurantService.get(RES1_ID));
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + RES1_ID + 100500)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RES7_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), RES7));
    }

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL + RES9_ID))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL + RES9_ID)
                .with(userHttpBasic(USER5)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + (RES9_ID + 100500))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(toToList(RESTAURANTS)));
    }

    @Test
    void testGetForDay() throws Exception {
        mockMvc.perform(get(REST_URL + RES2_ID + "/for?day=2019-07-01")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(List.of(RES2)));
    }

    @Test
    void testGetForDayNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + RES6_ID + "/for?day=2019-07-01")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void testGetAllForDay() throws Exception {
        mockMvc.perform(get(REST_URL + "for?day=2019-07-01")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(List.of(RES9, RES1, RES3, RES4, RES5, RES7, RES2)));
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(123, "not null id");
        mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void updateInvalidNullName() throws Exception {
        Restaurant invalid = new Restaurant(RES3);
        invalid.setName(null);
        mockMvc.perform(put(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print())
                .andExpect(detailMessage("name must not be blank"));
    }

    @Test
    void updateInvalidShortName() throws Exception {
        Restaurant invalid = new Restaurant(RES3);
        invalid.setName("z");
        mockMvc.perform(put(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print())
                .andExpect(detailMessage("name size must be between 2 and 100"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicateName() throws Exception {
        Restaurant duplicate = new Restaurant(null, RES5.getName());
        mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(duplicate)))
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print())
                .andExpect(detailMessage(ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT_NAME));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicateName() throws Exception {
        Restaurant duplicate = new Restaurant(RES5_ID, RES6.getName());
        mockMvc.perform(put(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(duplicate)))
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print())
                .andExpect(detailMessage(ExceptionInfoHandler.EXCEPTION_DUPLICATE_RESTAURANT_NAME));
    }
}