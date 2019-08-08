package ru.restaurant.voting.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.service.Dish.DishService;
import ru.restaurant.voting.service.restaurant.RestaurantService;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.to.RestaurantToWithStats;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.restaurant.voting.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    public Restaurant create(Restaurant restaurant) {
        log.info("create with name={}", restaurant.getName());
        checkNew(restaurant);
        return restaurantService.create(restaurant);
    }

    public void update(Restaurant restaurant) {
        log.info("update with id={}", restaurant.getId());
        restaurantService.update(restaurant);
    }

    public void delete(int id) {
        log.info("deleteWithId with id={}", id);
        restaurantService.delete(id);
    }

    public Restaurant get(int id) {
        log.info("get with id={}", id);
        return restaurantService.get(id);
    }

    public List<RestaurantTo> getAll() {
        log.info("getAl");
        return restaurantService.getAll();
    }

    public Restaurant getForDay(int id, LocalDate localDate) {
        log.info("getForDay {} with id={}", localDate, id);
        return restaurantService.getForDay(id, localDate);
    }

    public List<Restaurant> getAllForDay(LocalDate localDate) {
        log.info("getAllForDay {}", localDate);
        return restaurantService.getAllForDay(localDate);
    }

    public Vote vote(LocalDate date, int userId, int restaurantId, LocalTime time) {
        log.info("user with id={} voted for restaurant with id={}", userId, restaurantId);
        return restaurantService.vote(date, userId, restaurantId, time);
    }

    public int getStatForDay(LocalDate date, int restaurantId) {
        log.info("get stat for restaurant with id={}", restaurantId);
        return restaurantService.getStatForDay(date, restaurantId);
    }

    public int getStat(int restaurantId) {
        log.info("get stat for restaurant with id={}", restaurantId);
        return restaurantService.getStat(restaurantId);
    }

    public List<RestaurantToWithStats> getAllWithStat() {
        log.info("get all with stat");
        return restaurantService.getAllWithStats();
    }

    public List<RestaurantToWithStats> getAllWithStatForDay(LocalDate date) {
        log.info("get all with stat for day");
        return restaurantService.getAllWithStatsForDay(date);
    }

    public List<Dish> getAllDishes(int restaurantId) {
        log.info("get all dishes for restaurant with id={}", restaurantId);
        return dishService.getAll(restaurantId);
    }

    public Dish getDish(int id, int restaurantId) {
        log.info("get dish with id={} for restaurant with id={}", id, restaurantId);
        return dishService.get(id, restaurantId);
    }

    public void deleteDish(int id, int restaurantId) {
        log.info("delete dish with id={} for restaurant with id={}", id, restaurantId);
        dishService.delete(id, restaurantId);
    }

    public Dish createDish(Dish dish, int restaurantId) {
        log.info("create dish for restaurant with id={}", restaurantId);
        checkNew(dish);
        return dishService.create(dish, restaurantId);
    }

    public void updateDish(Dish dish, int restaurantId) {
        log.info("update dish with id={} for restaurant with id={}",dish.getId(), restaurantId);
        dishService.update(dish, restaurantId);
    }
}
