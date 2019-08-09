package ru.restaurant.voting.service.restaurant;

import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.to.RestaurantToWithStats;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RestaurantService {

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant);

    List<RestaurantTo> getAll();

    Restaurant get(int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    List<RestaurantToWithStats> getAllWithStats();

    Restaurant getForDay(int id, LocalDate localDate) throws NotFoundException;

    List<Restaurant> getAllForDay(LocalDate localDate);

    Vote vote(LocalDate date, int userId, int restaurantId, LocalTime time);

    int getStatForDay(LocalDate date, int restaurantId);

    int getStat(int restaurantId);

    List<RestaurantToWithStats> getAllWithStatsForDay(LocalDate date);
}
