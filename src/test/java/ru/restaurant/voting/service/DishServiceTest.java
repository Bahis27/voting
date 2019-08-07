package ru.restaurant.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.service.Dish.DishService;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.restaurant.voting.TestData.*;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    DishService dishService;

    @Test
    void create() throws Exception {
        Dish newDish = new Dish(null, "yummy", 500, RES4);
        Dish created = dishService.create(newDish);
        newDish.setId(created.getId());
        assertMatch(newDish, created);
    }

    @Test
    void duplicateCreate() throws Exception {
        Dish newDish = new Dish(DISH9);
        newDish.setId(null);
        newDish.setPrice(12);
        assertThrows(DataAccessException.class, () ->
                dishService.create(newDish));
    }

    @Test
    void update() throws Exception {
        Dish updated = new Dish(DISH7);
        updated.setPrice(500);
        dishService.update(new Dish(updated));
        assertMatch(updated, dishService.get(DISH7_ID), "dayMenus");
    }

    @Test
    void delete() throws Exception {
        dishService.delete(DISH15_ID);
        List<Dish> dishes = dishService.getAll(RES5_ID);
        assertFalse(dishes.contains(DISH15));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.delete(11));
    }

    @Test
    void get() throws Exception {
        Dish dish = dishService.get(DISH17_ID);
        assertMatch(dish, DISH17, "dayMenus");
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.get(25));
    }

    @Test
    void getAll() throws Exception {
        List<Dish> dishes = dishService.getAll(RES8_ID);
        assertMatch(dishes, DISH22, DISH23, DISH24);
    }
}
