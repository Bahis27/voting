package ru.restaurant.voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.restaurant.voting.TestUtil;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.repository.VoteRepository;
import ru.restaurant.voting.to.RestaurantToWithStats;
import ru.restaurant.voting.web.AbstractControllerTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.voting.TestData.*;
import static ru.restaurant.voting.TestUtil.userHttpBasic;
import static ru.restaurant.voting.util.exception.ErrorType.DATA_NOT_FOUND;
import static ru.restaurant.voting.util.exception.ErrorType.WRONG_REQUEST;

class UserRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository voteRepository;

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
    void testGetAllForEmptyDay() throws Exception {
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
    void testVote() throws Exception {
        mockMvc.perform(post(REST_URL + "/vote/" + RES8_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isConflict())
                .andExpect(errorType(WRONG_REQUEST))
                .andExpect(detailMessage("Restaurant with restaurantId = 108 has not DayMenu for this day"))
                .andDo(print());
    }

    @Test
    void testGetStatForDay() throws Exception {
        mockMvc.perform(get(REST_URL + RES5_ID + "/stat")
                .with(userHttpBasic(USER3)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(TestUtil.readFromJsonMvcResult(result, Integer.class)).isEqualTo(0))
                .andDo(print());
    }

    @Test
    void testGetAllWithStatForDay() throws Exception {
        mockMvc.perform(get(REST_URL + "stat")
                .with(userHttpBasic(USER4)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(TestUtil.readListFromJsonMvcResult(result, RestaurantToWithStats.class).isEmpty()))
                .andDo(print());
    }

    @Test
    void testGetAllDishes() throws Exception {
        mockMvc.perform(get(REST_URL + RES3_ID + "/dishes")
                .with(userHttpBasic(USER6)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(TestUtil.readListFromJsonMvcResult(result, Dish.class)).isEqualTo(RES3_DISHES))
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
}