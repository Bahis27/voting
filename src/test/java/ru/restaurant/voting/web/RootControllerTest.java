package ru.restaurant.voting.web;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.restaurant.voting.UserTestData.USER6;
import static ru.restaurant.voting.UserTestData.USER6_ID;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(9)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(USER6_ID)),
                                hasProperty("name", is(USER6.getName()))
                        )
                )));
    }
}