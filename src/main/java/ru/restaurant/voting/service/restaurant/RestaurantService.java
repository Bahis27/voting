package ru.restaurant.voting.service.restaurant;

import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant);

    void delete(int id) throws NotFoundException;

    Restaurant get(int id) throws NotFoundException;

    List<RestaurantTo> getAll();

    Restaurant getForDay(int id, LocalDate localDate) throws NotFoundException;

    List<Restaurant> getAllForDay(LocalDate localDate);

    Vote vote(LocalDate date, int userId, int restaurantId);
}
