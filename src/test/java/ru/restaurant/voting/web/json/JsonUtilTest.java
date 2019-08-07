package ru.restaurant.voting.web.json;

import org.junit.jupiter.api.Test;
import ru.restaurant.voting.TestData;
import ru.restaurant.voting.model.User;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {


    @Test
    void testWriteOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(TestData.USER4);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = TestData.jsonWithPassword(TestData.USER4, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}