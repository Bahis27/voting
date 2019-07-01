package ru.restaurant.voting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.restaurant.voting.model.User;
import ru.restaurant.voting.to.UserTo;
import ru.restaurant.voting.util.UserUtil;
import ru.restaurant.voting.web.AbstractControllerTest;
import ru.restaurant.voting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.voting.UserTestData.*;
import static ru.restaurant.voting.web.user.ProfileRestController.REST_URL;
import static ru.restaurant.voting.TestUtil.userHttpBasic;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER1));
    }

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER5, USER4, USER3, USER7, USER8, USER6, USER2);
    }

    @Test
    void testUpdate() throws Exception {
        UserTo updatedTo = new UserTo(USER1_ID, "newName", "newemail@ya.ru", "newPassword");
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER1), updatedTo));
    }
}