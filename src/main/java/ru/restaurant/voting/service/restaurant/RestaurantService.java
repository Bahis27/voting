package ru.restaurant.voting.service.restaurant;

import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.to.RestaurantToWithStats;
import ru.restaurant.voting.to.RestaurantNamesTo;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RestaurantService {

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant);

    void delete(int id) throws NotFoundException;

    Restaurant get(int id) throws NotFoundException;

    List<RestaurantNamesTo> getAll();

    List<RestaurantToWithStats> getAllWithStats();

    Restaurant getForDay(int id, LocalDate localDate) throws NotFoundException;

    List<Restaurant> getAllForDay(LocalDate localDate);

    Vote vote(LocalDate date, int userId, int restaurantId, LocalTime time);

    List<Dish> getAllDishes(int restaurantId);

    int getStatForDay(LocalDate date, int restaurantId);

    int getStat(int restaurantId);

    List<RestaurantToWithStats> getAllWithStatsForDay(LocalDate date);
}
