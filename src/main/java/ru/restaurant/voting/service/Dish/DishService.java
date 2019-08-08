package ru.restaurant.voting.service.Dish;

import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.util.List;

public interface DishService {
    Dish create(Dish dish);

    void update(Dish dish);

    void delete(int id, int restaurantId) throws NotFoundException;

    Dish get(int id, int restaurantId) throws NotFoundException;

    List<Dish> getAll(int restaurantId);
}
