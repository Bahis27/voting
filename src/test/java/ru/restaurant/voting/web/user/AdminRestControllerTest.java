package ru.restaurant.voting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.voting.model.Role;
import ru.restaurant.voting.model.User;
import ru.restaurant.voting.web.AbstractControllerTest;
import ru.restaurant.voting.web.json.JsonUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.voting.TestData.*;
import static ru.restaurant.voting.TestUtil.*;
import static ru.restaurant.voting.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.restaurant.voting.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;

class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, User.class), ADMIN, "password", "registered"));
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void testGetAllUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAllForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER5)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + USER6.getEmail())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, User.class), USER6, "password", "registered"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER3_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER5, USER1, USER4, USER7, USER8, USER6, USER2);
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void testUpdate() throws Exception {
        User updated = new User(USER4);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER4_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(updated, USER4.getPassword())))
                .andExpect(status().isNoContent());

        assertMatch(userService.get(USER4_ID), updated, "password", "registered");
    }

    @Test
    void testCreate() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(expected, "newPass")))
                .andExpect(status().isCreated());

        User returned = readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected, "password", "registered");
        assertMatch(userService.getAll(), ADMIN, USER5, USER1, USER4, expected, USER3, USER7, USER8, USER6, USER2);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(User.class, ADMIN, USER5, USER1, USER4, USER3, USER7, USER8, USER6, USER2));
    }

    @Test
    void testCreateInvalid() throws Exception {
        User expected = new User(null, null, "", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testUpdateInvalid() throws Exception {
        User updated = new User(USER3);
        updated.setName("");
        mockMvc.perform(put(REST_URL + USER3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testUpdateDuplicate() throws Exception {
        User updated = new User(USER2);
        updated.setEmail("admin@gmail.com");
        mockMvc.perform(put(REST_URL + USER2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(updated, "password")))
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_EMAIL))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testCreateDuplicate() throws Exception {
        User expected = new User(null, "New", "simple@mail.ru", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(expected, "newPass")))
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_EMAIL));

    }

    @Test
    void testCreateUnsafe() throws Exception {
        User invalid = new User(null, "<script>alert(123)</script>", "new@gmail.com", "newPass", Role.ROLE_USER);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(invalid, "newPass")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }
}