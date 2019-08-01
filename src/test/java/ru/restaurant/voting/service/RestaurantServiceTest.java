package ru.restaurant.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.repository.VoteRepository;
import ru.restaurant.voting.service.restaurant.RestaurantService;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.util.exception.NotFoundException;
import ru.restaurant.voting.util.exception.UserAlreadyHasVotedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.voting.RestaurantTestData.*;
import static ru.restaurant.voting.UserTestData.ADMIN_ID;

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
        assertMatch(updated, restaurantService.get(RES4_ID));
    }

    @Test
    void delete() throws Exception {
        restaurantService.delete(RES5_ID);
        assertMatch(restaurantService.getAll(), RES1, RES2, RES3, RES4, RES6, RES7, RES8, RES9);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.delete(1));
    }

    @Test
    void get() throws Exception {
        Restaurant restaurant = restaurantService.get(RES5_ID);
        assertMatch(restaurant, RES5);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                restaurantService.get(95));
    }

    @Test
    void getAll() throws Exception {
        List<RestaurantTo> all = restaurantService.getAll();
        assertMatch(all, RES1, RES2, RES3, RES4, RES5, RES6, RES7, RES8, RES9);
    }

    @Test
    void getForDay() throws Exception {
        Restaurant restaurant = restaurantService.getForDay(RES8_ID, LocalDate.parse("2019-07-03"));
        assertMatch(restaurant, RES8);
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

        LocalTime time2 = LocalTime.of(10, 59, 0);
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
    void getAllDishes() throws Exception {
        List<Dish> dishes = restaurantService.getAllDishes(RES3_ID);
        assertThat(dishes).usingDefaultElementComparator().isEqualTo(RES3_DISHES);
    }
}