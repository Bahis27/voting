package ru.restaurant.voting.service.daymenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.repository.DayMenuRepository;
import ru.restaurant.voting.repository.DishRepository;
import ru.restaurant.voting.repository.RestaurantRepository;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;
import static ru.restaurant.voting.util.ValidationUtil.checkNew;

@Service
public class DayMenuServiceImpl implements DayMenuService {

    private final DayMenuRepository dayMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Autowired
    public DayMenuServiceImpl(DayMenuRepository dayMenuRepository,
                              RestaurantRepository restaurantRepository,
                              DishRepository dishRepository) {
        this.dayMenuRepository = dayMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @Transactional
    @Override
    public DayMenu create(DayMenu dayMenu, int restaurantId, int dishId) {
        Assert.notNull(dayMenu, "dayMenu must not be null");
        checkNew(dayMenu);
        dayMenu.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        dayMenu.setDish(dishRepository.get(dishId, restaurantId));
        return dayMenuRepository.save(dayMenu);
    }

    @Transactional
    @Override
    public void update(DayMenu dayMenu, int restaurantId, int dishId) {
        Assert.notNull(dayMenu, "dayMenu must not be null");
        checkNotFoundWithId(dayMenuRepository.get(dayMenu.getId(), restaurantId), dayMenu.getId());
        dayMenu.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        dayMenu.setDish(dishRepository.get(dishId, restaurantId));
        dayMenuRepository.save(dayMenu);
    }

    @Override
    public List<DayMenu> getAll(int restaurantId) {
        return dayMenuRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public List<DayMenu> getAllForDay(int restaurantId, LocalDate date) throws NotFoundException {
        return dayMenuRepository.getAllForDateAndRestaurantId(restaurantId, date);
    }

    @Override
    public DayMenu get(int id, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(dayMenuRepository.get(id, restaurantId), id);
    }

    @Override
    public void delete(int id, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(dayMenuRepository.delete(id, restaurantId), id);
    }

    @Override
    public void deleteAllForDay(int restaurantId, LocalDate date) throws NotFoundException {
        checkNotFoundWithId(dayMenuRepository.deleteAll(restaurantId, date), restaurantId);
    }
}
