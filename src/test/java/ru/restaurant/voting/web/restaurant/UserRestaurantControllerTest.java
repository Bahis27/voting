package ru.restaurant.voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.restaurant.voting.TestUtil;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.repository.VoteRepository;
import ru.restaurant.voting.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
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
    private VoteRepository voteRepository;

    private static final String REST_URL = UserRestaurantController.REST_URL + '/';

    @Test
    void getForDayNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + RES8_ID)
                .with(userHttpBasic(USER5)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND))
                .andDo(print());
    }

    @Test
    void getForDayUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL + RES8_ID))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getAllForEmptyDay() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER5)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Restaurant.class, new ArrayList<Restaurant>()))
                .andDo(print());
    }

    @Test
    void getAllForDayUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void testVoteFirstTime() throws Exception {
        LocalDate toDay = LocalDate.now();

        Vote created = new Vote(toDay, ADMIN_ID, RES8_ID);
        ResultActions action = mockMvc.perform(post(REST_URL + "/vote/" + RES8_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        Vote returned = readFromJson(action, Vote.class);
        assertThat(returned).isEqualTo(created);

        Vote saved = voteRepository.findByUserIdAndVotingDate(ADMIN_ID, toDay).orElse(null);
        assertThat(created).isEqualTo(saved);
    }

    @Test
    void testVoteSecondTime() throws Exception {
        LocalDate toDay = LocalDate.now();

        Vote vote1 = new Vote(toDay, ADMIN_ID, RES4_ID);
        mockMvc.perform(post(REST_URL + "/vote/" + RES4_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        Vote vote2 = new Vote(toDay, ADMIN_ID, RES5_ID);

        if (LocalTime.now().isBefore(LocalTime.of(11, 0, 0))) {
            mockMvc.perform(post(REST_URL + "/vote/" + RES5_ID)
                    .with(userHttpBasic(ADMIN)))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andDo(print());
            Vote saved = voteRepository.findByUserIdAndVotingDate(ADMIN_ID, toDay).orElse(null);

            assertThat(vote2).isEqualTo(saved);

            assertThat(vote1).isNotEqualTo(saved);
        } else {
            mockMvc.perform(post(REST_URL + "/vote/" + RES5_ID)
                    .with(userHttpBasic(ADMIN)))
                    .andExpect(status().isConflict())
                    .andExpect(errorType(WRONG_REQUEST))
                    .andDo(print());
        }
    }

    @Test
    void testGetAllDishes() throws Exception {
        mockMvc.perform(get(REST_URL + RES3_ID + "/all")
                .with(userHttpBasic(USER6)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(TestUtil.readListFromJsonMvcResult(result, Dish.class)).isEqualTo(RES3_DISHES))
                .andDo(print());
    }
}