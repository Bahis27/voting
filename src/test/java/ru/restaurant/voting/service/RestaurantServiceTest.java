package ru.restaurant.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.repository.VoteRepository;
import ru.restaurant.voting.service.restaurant.RestaurantService;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.to.RestaurantToWithStats;
import ru.restaurant.voting.util.ToUtil;
import ru.restaurant.voting.util.exception.NotFoundException;
import ru.restaurant.voting.util.exception.RestaurantHasNotMenuForThisDay;
import ru.restaurant.voting.util.exception.UserAlreadyHasVotedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.voting.TestData.*;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteRepository voteRepository;

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "new one");
        Restaurant created = restaurantService.create(newRestaurant);
        newRestaurant.setId(created.getId());
        assertMatch(newRestaurant, created);
    }

    @Test
    void duplicateNameCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                restaurantService.create(new Restaurant(null, "Pita Pan")));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = new Restaurant(RES4);
        updated.setName("updated");
        restaurantService.update(new Restaurant(updated));
        assertMatch(updated, restaurantService.get(RES4_ID), "dayMenus");
    }

    @Test
    void delete() throws Exception {
        assertMatch(restaurantService.getAllForDay(LocalDate.of(2019, 7, 1)), RESTAURANTS_FOR_DAY_20190701);
        restaurantService.delete(RES1_ID);
        assertMatch(restaurantService.getAllForDay(LocalDate.of(2019, 7, 1)), RESTAURANTS_FOR_DAY_20190701_WITHOUT_RES1);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.delete(1));
    }

    @Test
    void get() throws Exception {
        Restaurant restaurant = restaurantService.get(RES5_ID);
        assertMatch(restaurant, RES5, "dayMenus");
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.get(95));
    }

    @Test
    void getAll() throws Exception {
        List<RestaurantTo> all = restaurantService.getAll();
        assertMatch(all, ToUtil.restaurantsAsToList(RESTAURANTS));
    }

    @Test
    void getForDay() throws Exception {
        Restaurant restaurant = restaurantService.getForDay(RES8_ID, LocalDate.parse("2019-07-03"));
        assertMatch(restaurant, RES8, "dayMenus");
    }

    @Test
    void getForDayNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.getForDay(RES8_ID, LocalDate.parse("2019-07-01")));
    }

    @Test
    void getAllForDay() throws Exception {
        List<Restaurant> restaurants = restaurantService.getAllForDay(LocalDate.parse("2019-07-01"));
        assertMatch(restaurants, RES9, RES1, RES3, RES4, RES5, RES7, RES2);
    }

    @Test
    void getAllForEmptyDay() throws Exception {
        List<Restaurant> restaurants = restaurantService.getAllForDay(LocalDate.parse("2019-07-05"));
        assertMatch(restaurants, List.of());
    }

    @Test
    void voteForRestaurantWithOutDayMenu() throws Exception {
        assertThrows(RestaurantHasNotMenuForThisDay.class, () ->
                restaurantService.vote(LocalDate.of(2019, 7, 7), USER4_ID, RES9_ID, LocalTime.now()));
    }

    @Test
    void voteFirstTimeBefore11() throws Exception {
        LocalDate date = LocalDate.parse("2019-07-01");
        LocalTime time = LocalTime.of(9, 0, 0);
        Vote vote = restaurantService.vote(date, ADMIN_ID, RES1_ID, time);
        assertEquals(vote, voteRepository.findByUserIdAndVotingDate(ADMIN_ID, date).orElse(null));
    }

    @Test
    void voteSecondTimeBefore11() throws Exception {
        LocalDate date = LocalDate.parse("2019-07-01");
        LocalTime time1 = LocalTime.of(9, 0, 0);;
        Vote vote1 = restaurantService.vote(date, ADMIN_ID, RES1_ID, time1);

        LocalTime time2 = LocalTime.of(10, 0, 0);
        Vote vote2 = restaurantService.vote(date, ADMIN_ID, RES2_ID, time2);

        Vote expected = voteRepository.findByUserIdAndVotingDate(ADMIN_ID, date).orElse(null);
        assertEquals(vote2, expected);
        assertNotEquals(vote1, expected);
    }

    @Test
    void voteFirstTimeAfter11() throws Exception {
        LocalDate date = LocalDate.parse("2019-07-02");
        LocalTime time = LocalTime.of(11, 0, 5);
        Vote vote = restaurantService.vote(date, ADMIN_ID, RES3_ID, time);
        assertEquals(vote, voteRepository.findByUserIdAndVotingDate(ADMIN_ID, date).orElse(null));
    }

    @Test
    void voteSecondTimeAfter11() throws Exception {
        LocalDate date = LocalDate.parse("2019-07-02");
        LocalTime time1 = LocalTime.of(11, 0, 5);
        Vote expected = restaurantService.vote(date, ADMIN_ID, RES3_ID, time1);

        LocalTime time2 = LocalTime.of(12, 0, 0);
        assertThrows(UserAlreadyHasVotedException.class, () ->
                restaurantService.vote(date, ADMIN_ID, RES4_ID, time2));

        assertEquals(expected, voteRepository.findByUserIdAndVotingDate(ADMIN_ID, date).orElse(null));
    }

    @Test
    void getStat() throws Exception {
        assertEquals(restaurantService.getStat(RES8_ID), RES4_STAT);
    }

    @Test
    void getStatForDay() throws Exception {
        assertEquals(restaurantService.getStatForDay(LocalDate.of(2019, 7, 3), RES8_ID), RES4_STAT_20190703);
    }

    @Test
    void getAllWithStats() throws Exception {
        List<RestaurantToWithStats> allWithStats = restaurantService.getAllWithStats();
        assertMatch(allWithStats, RESTAURANTS_WITH_STAT);
    }

    @Test
    void getAllWithStatsForDay() throws Exception {
        List<RestaurantToWithStats> allWithStatsForDay = restaurantService.getAllWithStatsForDay(LocalDate.of(2019, 7, 3));
        assertMatch(allWithStatsForDay, RESTAURANTS_WITH_STAT_FORDAY);
    }
}