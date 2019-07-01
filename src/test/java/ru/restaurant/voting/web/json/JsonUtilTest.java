package ru.restaurant.voting.web.json;

import org.junit.jupiter.api.Test;
import ru.restaurant.voting.UserTestData;
import ru.restaurant.voting.model.User;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {


    @Test
    void testWriteOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER4);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER4, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}