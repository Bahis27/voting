package ru.restaurant.voting.service.Dish;

import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.to.DishTo;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.util.List;

public interface DishService {
    Dish create(Dish dish, int restaurantId);

    void update(Dish dish, int restaurantId);

    List<DishTo> getAll();

    List<Dish> getAll(int restaurantId);

    Dish get(int id, int restaurantId) throws NotFoundException;

    void delete(int id, int restaurantId) throws NotFoundException;
}
