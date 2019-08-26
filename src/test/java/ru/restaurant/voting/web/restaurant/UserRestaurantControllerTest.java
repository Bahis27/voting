package ru.restaurant.voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.service.vote.VoteService;
import ru.restaurant.voting.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.voting.TestData.*;
import static ru.restaurant.voting.TestUtil.readFromJson;
import static ru.restaurant.voting.TestUtil.userHttpBasic;
import static ru.restaurant.voting.util.exception.ErrorType.DATA_NOT_FOUND;
import static ru.restaurant.voting.util.exception.ErrorType.WRONG_REQUEST;

class UserRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    VoteService voteService;

    private static final String REST_URL = UserRestaurantController.REST_URL + '/';

    @Test
    void testGetForDayNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + RES8_ID)
                .with(userHttpBasic(USER5)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void testGetForDayUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL + RES8_ID))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void testGetAllForToday() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER5)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Restaurant.class, new ArrayList<Restaurant>()))
                .andDo(print());
    }

    @Test
    void testGetAllForDayUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void testGetAllDayMenusForDayByRestaurantId() throws Exception {
        mockMvc.perform(get(REST_URL + RES8_ID + "/menus")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DayMenu.class, List.of()))
                .andDo(print());
    }

    @Test
    void testGetAllDayMenusForDay() throws Exception {
        mockMvc.perform(get(REST_URL + "/menus")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DayMenu.class, List.of()))
                .andDo(print());
    }

    @Test
    void testVoteFirstTime() throws Exception {
        LocalDate nowDate = LocalDate.now();
        Vote newVote = new Vote(null, nowDate, USER3, RES8);
        ResultActions action = mockMvc.perform(post(REST_URL + RES8_ID + "/vote")
                .with(userHttpBasic(USER3)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        Vote returned = readFromJson(action, Vote.class);
        newVote.setId(returned.getId());

        assertMatch(newVote, returned, "restaurant");
    }

    @Test
    void testVoteSecondTime() throws Exception {
        LocalDate nowDate = LocalDate.now();

        ResultActions action1 = mockMvc.perform(post(REST_URL + RES8_ID + "/vote")
                .with(userHttpBasic(USER3)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
        Vote returned1 = readFromJson(action1, Vote.class);
        Vote inDBVote = voteService.getByUserIdAndVotingDate(USER3_ID, nowDate).orElse(null);

        assertEquals(returned1, inDBVote);

        LocalTime time = LocalTime.now();
        if (time.isBefore(LocalTime.of(11, 0, 0))) {
            ResultActions action2 = mockMvc.perform(post(REST_URL + RES4_ID + "/vote")
                    .with(userHttpBasic(USER3)))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andDo(print());
            Vote returned2 = readFromJson(action2, Vote.class);
            inDBVote = voteService.getByUserIdAndVotingDate(USER3_ID, nowDate).orElse(null);

            assertNotEquals(returned1, inDBVote);
            assertEquals(returned2, inDBVote);
        } else {
            mockMvc.perform(post(REST_URL + RES4_ID + "/vote")
                    .with(userHttpBasic(USER3)))
                    .andExpect(status().isConflict())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(errorType(WRONG_REQUEST))
                    .andDo(print());

            assertEquals(returned1, inDBVote);
        }
    }
}