package ru.restaurant.voting.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.service.restaurant.RestaurantService;

import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.voting.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;

    public Restaurant create(Restaurant restaurant) {
        log.info("create with name={}", restaurant.getName());
        checkNew(restaurant);
        return service.create(restaurant);
    }

    public void update(Restaurant restaurant) {
        log.info("update with id={}", restaurant.getId());
        service.update(restaurant);
    }

    public void delete(int id) {
        log.info("delete with id={}", id);
        service.delete(id);
    }

    public Restaurant get(int id) {
        log.info("get with id={}", id);
        return service.get(id);
    }

    public List<Restaurant> getAll() {
        log.info("getAl");
        return service.getAll();
    }

    public Restaurant getForDay(int id, LocalDate localDate) {
        log.info("getForDay {} with id={}", localDate, id);
        return service.getForDay(id, localDate);
    }

    public List<Restaurant> getAllForDay(LocalDate localDate) {
        log.info("getAllForDay {}", localDate);
        return service.getAllForDay(localDate);
    }
}
