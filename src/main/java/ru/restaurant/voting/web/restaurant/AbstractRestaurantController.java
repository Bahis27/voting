package ru.restaurant.voting.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.service.Dish.DishService;
import ru.restaurant.voting.service.daymenu.DayMenuService;
import ru.restaurant.voting.service.restaurant.RestaurantService;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.to.RestaurantToWithStats;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    @Autowired
    private DayMenuService dayMenuService;

    public Restaurant create(Restaurant restaurant) {
        log.info("create restaurant with name={}", restaurant.getName());
        return restaurantService.create(restaurant);
    }

    public void update(Restaurant restaurant) {
        log.info("update restaurant with id={}", restaurant.getId());
        restaurantService.update(restaurant);
    }

    public List<RestaurantTo> getAll() {
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    public Restaurant get(int id) {
        log.info("get restaurant with id={}", id);
        return restaurantService.get(id);
    }

    public void delete(int id) {
        log.info("delete restaurant with id={}", id);
        restaurantService.delete(id);
    }

    public Restaurant getForDay(int id, LocalDate localDate) {
        log.info("get restaurant with id={}", id);
        return restaurantService.getForDay(id, localDate);
    }

    public List<Restaurant> getAllForDay(LocalDate localDate) {
        log.info("get all restaurants");
        return restaurantService.getAllForDay(localDate);
    }

    //vote
    public Vote vote(LocalDate date, int userId, int restaurantId, LocalTime time) {
        log.info("user with id={} voted for restaurant with id={}", userId, restaurantId);
        return restaurantService.vote(date, userId, restaurantId, time);
    }

    //stat
    public int getStatForDay(LocalDate date, int restaurantId) {
        log.info("get stat for restaurant with id={}", restaurantId);
        return restaurantService.getStatForDay(date, restaurantId);
    }

    public int getStat(int restaurantId) {
        log.info("get stat for restaurant with id={}", restaurantId);
        return restaurantService.getStat(restaurantId);
    }

    public List<RestaurantToWithStats> getAllWithStat() {
        log.info("get all restaurants with stat");
        return restaurantService.getAllWithStats();
    }

    public List<RestaurantToWithStats> getAllWithStatForDay(LocalDate date) {
        log.info("get all restaurant with stat");
        return restaurantService.getAllWithStatsForDay(date);
    }

    //dishes
    public Dish createDish(Dish dish, int restaurantId) {
        log.info("create dish with name={} for restaurant with id={}", dish.getName(), restaurantId);
        return dishService.create(dish, restaurantId);
    }

    public void updateDish(Dish dish, int restaurantId) {
        log.info("update dish with id={} for restaurant with id={}", dish.getId(), restaurantId);
        dishService.update(dish, restaurantId);
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

    //dayMenus
    public DayMenu createDayMenu(DayMenu dayMenu, int restaurantId, int dishId) {
        log.info("create dayMenu for restaurant with id={} and dish with id={}", restaurantId, dishId);
        return dayMenuService.create(dayMenu, restaurantId, dishId);
    }

    public void updateDayMenu(DayMenu dayMenu, int restaurantId, int dishId) {
        log.info("update dayMenu with id={} for restaurant with id={} and dish with id={}", dayMenu.getId(), restaurantId, dishId);
        dayMenuService.update(dayMenu, restaurantId, dishId);
    }

    public List<DayMenu> getAllDayMenus(int restaurantId) {
        log.info("get all dayMenus for restaurant with id={}", restaurantId);
        return dayMenuService.getAll(restaurantId);
    }

    public List<DayMenu> getAllDayMenusForDay(int restaurantId, LocalDate date) {
        log.info("get all dayMenus for restaurant with id={}", restaurantId);
        return dayMenuService.getAllForDay(restaurantId, date);
    }

    public DayMenu getDayMenu(int id, int restaurantId) {
        log.info("get dayMenu with id={} for restaurant with id={}", id, restaurantId);
        return dayMenuService.get(id, restaurantId);
    }

    public void deleteDayMenu(int id, int restaurantId) {
        log.info("delete dayMenu with id={} for restaurant with id={}", id, restaurantId);
        dayMenuService.delete(id, restaurantId);
    }

    public void deleteAllDayMenusForDay(int restaurantId, LocalDate date) {
        log.info("delete all dayMenus for restaurant with id={}", restaurantId);
        dayMenuService.deleteAllForDay(restaurantId, date);
    }
}
