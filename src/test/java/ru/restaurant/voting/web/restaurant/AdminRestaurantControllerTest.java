package ru.restaurant.voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.voting.TestUtil;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.service.daymenu.DayMenuService;
import ru.restaurant.voting.service.dish.DishService;
import ru.restaurant.voting.service.restaurant.RestaurantService;
import ru.restaurant.voting.to.DayMenuTO;
import ru.restaurant.voting.to.DishTo;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.to.RestaurantToWithStats;
import ru.restaurant.voting.util.ToUtil;
import ru.restaurant.voting.util.exception.NotFoundException;
import ru.restaurant.voting.web.AbstractControllerTest;
import ru.restaurant.voting.web.ExceptionInfoHandler;
import ru.restaurant.voting.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.voting.TestData.*;
import static ru.restaurant.voting.TestUtil.*;
import static ru.restaurant.voting.util.exception.ErrorType.*;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    @Autowired
    private DayMenuService dayMenuService;

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
    void testCreateHtmlUnsafe() throws Exception {
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
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantTo.class, ToUtil.restaurantsAsToList(RESTAURANTS)));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RES7_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), RES7, "dayMenus"));
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
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RES1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(restaurantService.getAllForDay(LocalDate.of(2019, 7, 1)), RESTAURANTS_FOR_DAY_20190701_WITHOUT_RES1);
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
    void testGetForDay() throws Exception {
        mockMvc.perform(get(REST_URL + RES2_ID + "/for?day=2019-07-01")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Restaurant.class, List.of(RES2)));
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
                .andExpect(contentJson(Restaurant.class, List.of(RES9, RES1, RES3, RES4, RES5, RES7, RES2)));
    }

    @Test
    void testCreateInvalid() throws Exception {
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
    void testUpdateInvalidNullName() throws Exception {
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
    void testUpdateInvalidShortName() throws Exception {
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
    void testCreateDuplicateName() throws Exception {
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
    void testUpdateDuplicateName() throws Exception {
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

    //stat
    @Test
    void testGetStatForDay() throws Exception {
        mockMvc.perform(get(REST_URL + RES8_ID + "/stat/for?day=2019-07-03")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(TestUtil.readFromJsonMvcResult(result, Integer.class)).isEqualTo(3))
                .andDo(print());
    }

    @Test
    void testGetStat() throws Exception {
        mockMvc.perform(get(REST_URL + RES8_ID + "/stat")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(TestUtil.readFromJsonMvcResult(result, Integer.class)).isEqualTo(4))
                .andDo(print());
    }

    @Test
    void testGetAllWithStat() throws Exception {
        mockMvc.perform(get(REST_URL + "/stat")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantToWithStats.class, RESTAURANTS_WITH_STAT))
                .andDo(print());
    }

    @Test
    void testGetAllWithStatForDay() throws Exception {
        mockMvc.perform(get(REST_URL + "stat/for?day=2019-07-03")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantToWithStats.class, RESTAURANTS_WITH_STAT_FORDAY))
                .andDo(print());
    }

    //dishes
    @Test
    void testGetAllDishes() throws Exception {
        mockMvc.perform(get(REST_URL + "/dishes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DishTo.class, ToUtil.dishesAsToList(DISHES)));
    }

    @Test
    void testGetAllDishesForRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + RES3_ID + "/dishes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Dish.class, RES3_DISHES))
                .andDo(print());
    }

    @Test
    void testGetDish() throws Exception {
        mockMvc.perform(get(REST_URL + RES3_ID + "/dishes/" + DISH7_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Dish.class), DISH7, "restaurant"))
                .andDo(print());
    }

    @Test
    void testGetNotRestaurantsDish() throws Exception {
        mockMvc.perform(get(REST_URL + RES3_ID + "/dishes/" + DISH15_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void testDeleteDish() throws Exception {
        mockMvc.perform(delete(REST_URL + RES3_ID + "/dishes/" + DISH7_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(dishService.getAll(RES3_ID), DISH8, DISH9);
        assertThrows(NotFoundException.class, () -> dishService.get(DISH7_ID, RES3_ID));
    }

    @Test
    void testCreateDish() throws Exception {
        Dish created = new Dish(null, "Yummy", 100500);
        ResultActions action = mockMvc.perform(post(REST_URL + RES9_ID + "/dishes")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isOk())
                .andDo(print());

        Dish returned = readFromJson(action, Dish.class);
        created.setId(returned.getId());
        assertMatch(created, returned, "restaurant");

        Dish saved = dishService.get(returned.getId(), RES9_ID);
        assertMatch(returned, saved, "restaurant");
    }

    @Test
    void testCreateNotNewDish() throws Exception {
        Dish notNewDish = new Dish(DISH16);
        mockMvc.perform(post(REST_URL + RES1_ID + "/dishes")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(notNewDish)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testUpdateDish() throws Exception {
        Dish updated = new Dish(DISH11_ID, "Updated dish", 700);
        mockMvc.perform(put(REST_URL + RES4_ID + "/dishes")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(dishService.get(DISH11_ID, RES4_ID), updated, "restaurant");
    }

    //dayMenus
    @Test
    void testGetAllDayMenus() throws Exception {
        mockMvc.perform(get(REST_URL + "/menus")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DayMenuTO.class, ToUtil.dayMenusAsToList(DAY_MENUS)));
    }

    @Test
    void testGetAllDayMenusForRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + RES8_ID + "/menus")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DayMenu.class, RES8_DAYMENUS))
                .andDo(print());
    }

    @Test
    void testGetAllDayMenusForDayByRestaurantId() throws Exception {
        mockMvc.perform(get(REST_URL + RES8_ID + "/menus/for?day=2019-07-02")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DayMenu.class, DAYMENU24, DAYMENU26))
                .andDo(print());
    }

    @Test
    void testGetAllDayMenusForDay() throws Exception {
        mockMvc.perform(get(REST_URL + "/menus/for?day=2019-07-02")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DayMenu.class, DAYMENUS_FOR_20190702))
                .andDo(print());
    }

    @Test
    void testGetDayMenu() throws Exception {
        mockMvc.perform(get(REST_URL + RES2_ID + "/menus/" + DAYMENU17_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, DayMenu.class), DAYMENU17, "restaurant"))
                .andDo(print());
    }

    @Test
    void testDeleteDayMenu() throws Exception {
        mockMvc.perform(delete(REST_URL + RES7_ID + "/menus/" + DAYMENU37_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(dayMenuService.getAll(RES7_ID), DAYMENU9, DAYMENU10, DAYMENU11, DAYMENU23);
        assertThrows(NotFoundException.class, () -> dishService.get(DAYMENU37_ID, RES7_ID));
    }

    @Test
    void testDeleteAllDayMenuDorDay() throws Exception {
        mockMvc.perform(delete(REST_URL + RES1_ID + "/menus/for?day=2019-07-01")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertTrue(dayMenuService.getAllForDayByRestaurantId(RES1_ID, LocalDate.of(2019, 7, 1)).isEmpty());
    }

    @Test
    void testCreateDayMenu() throws Exception {
        DayMenu created = new DayMenu(null, null);
        ResultActions action = mockMvc.perform(post(REST_URL + RES4_ID + "/menus/" + DISH11_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isOk())
                .andDo(print());

        DayMenu returned = readFromJson(action, DayMenu.class);
        created.setId(returned.getId());
        assertMatch(created, returned, "restaurant", "dish");

        DayMenu saved = dayMenuService.get(returned.getId(), RES4_ID);
        assertMatch(returned, saved, "restaurant");
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testCreateDuplicateDayMenu() throws Exception {
        DayMenu created = new DayMenu(DAYMENU27);
        created.setId(null);
        mockMvc.perform(post(REST_URL + RES1_ID + "/menus/" + DISH1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isConflict())
                .andExpect(errorType(DATA_ERROR))
                .andDo(print());
    }

    @Test
    void testUpdateDayMenu() throws Exception {
        DayMenu updated = new DayMenu(DAYMENU39);
        updated.setMenuDate(null);
        mockMvc.perform(put(REST_URL + RES9_ID + "/menus/" + DISH26_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(dayMenuService.get(DAYMENU39_ID, RES9_ID), updated, "restaurant");
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateWithAlreadyExistingDayMenuInThisDay() throws Exception {
        DayMenu updated = new DayMenu(DAYMENU1);
        mockMvc.perform(put(REST_URL + RES1_ID + "/menus/" + DISH2_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict())
                .andExpect(errorType(DATA_ERROR))
                .andDo(print());
    }
}