package ru.restaurant.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.service.Dish.DishService;
import ru.restaurant.voting.util.exception.IllegalRequestDataException;
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
        Dish newDish = new Dish(null, "yummy", 500);
        Dish created = dishService.create(newDish, RES9_ID);
        newDish.setId(created.getId());
        assertMatch(newDish, created);
    }

    @Test
    void createNotNew() throws Exception {
        Dish notNewDish = new Dish(DISH17);
        assertThrows(IllegalRequestDataException.class, () ->
                dishService.create(notNewDish, RES6_ID));
    }

    @Test
    void duplicateCreate() throws Exception {
        Dish newDish = new Dish(DISH9);
        newDish.setId(null);
        newDish.setPrice(12);
        assertThrows(DataAccessException.class, () ->
                dishService.create(newDish, RES3_ID));
    }

    @Test
    void update() throws Exception {
        Dish updated = new Dish(DISH7);
        updated.setPrice(500);
        dishService.update(new Dish(updated), RES3_ID);
        assertMatch(updated, dishService.get(DISH7_ID, RES3_ID), "dayMenus");
    }

    @Test
    void updateNotRestaurantDish() throws Exception {
        Dish updated = new Dish(DISH18);
        updated.setPrice(500);
        assertThrows(NotFoundException.class, () ->
                dishService.update(new Dish(updated), RES4_ID));
    }

    @Test
    void delete() throws Exception {
        dishService.delete(DISH15_ID, RES5_ID);
        List<Dish> dishes = dishService.getAll(RES5_ID);
        assertFalse(dishes.contains(DISH15));
    }

    @Test
    void deleteNotRestaurantDish() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.delete(DISH1_ID,RES9_ID));
    }

    @Test
    void get() throws Exception {
        Dish dish = dishService.get(DISH20_ID, RES7_ID);
        assertMatch(dish, DISH20, "dayMenus");
    }

    @Test
    void getNotRestaurantsDish() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.get(DISH20_ID, RES9_ID));
    }

    @Test
    void getAll() throws Exception {
        List<Dish> dishes = dishService.getAll(RES8_ID);
        assertMatch(dishes, DISH22, DISH23, DISH24);
    }
}
