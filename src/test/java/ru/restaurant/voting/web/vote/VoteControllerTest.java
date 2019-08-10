package ru.restaurant.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.voting.TestData.*;
import static ru.restaurant.voting.TestUtil.readFromJsonMvcResult;
import static ru.restaurant.voting.TestUtil.userHttpBasic;

public class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + "/";

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Vote.class, VOTES));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE5_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Vote.class), VOTE5));
    }
}
