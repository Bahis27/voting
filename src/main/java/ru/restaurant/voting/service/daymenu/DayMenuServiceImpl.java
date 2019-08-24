package ru.restaurant.voting.service.daymenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.repository.DayMenuRepository;
import ru.restaurant.voting.repository.RestaurantRepository;
import ru.restaurant.voting.service.dish.DishService;
import ru.restaurant.voting.to.DayMenuTO;
import ru.restaurant.voting.util.ToUtil;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.voting.util.ValidationUtil.checkNew;
import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DayMenuServiceImpl implements DayMenuService {

    private final DayMenuRepository dayMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishService dishService;

    @Autowired
    public DayMenuServiceImpl(DayMenuRepository dayMenuRepository,
                              RestaurantRepository restaurantRepository,
                              DishService dishService) {
        this.dayMenuRepository = dayMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishService = dishService;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    @Override
    public DayMenu create(DayMenu dayMenu, int restaurantId, int dishId) {
        Assert.notNull(dayMenu, "dayMenu must not be null");
        checkNew(dayMenu);
        if (dayMenu.getMenuDate() == null) {
            dayMenu.setMenuDate(LocalDate.now());
        }
        dayMenu.setRestaurant(restaurantRepository.getOne(restaurantId));
        dayMenu.setDish(dishService.get(dishId, restaurantId));
        return dayMenuRepository.save(dayMenu);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    @Override
    public void update(DayMenu dayMenu, int restaurantId, int dishId) {
        Assert.notNull(dayMenu, "dayMenu must not be null");
        checkNotFoundWithId(dayMenuRepository.get(dayMenu.getId(), restaurantId), dayMenu.getId());
        if (dayMenu.getMenuDate() == null) {
            dayMenu.setMenuDate(LocalDate.now());
        }
        dayMenu.setRestaurant(restaurantRepository.getOne(restaurantId));
        dayMenu.setDish(dishService.get(dishId, restaurantId));
        dayMenuRepository.save(dayMenu);
    }

    @Override
    public List<DayMenuTO> getAll() {
        return ToUtil.dayMenusAsToList(dayMenuRepository.getAll());
    }

    @Override
    public List<DayMenu> getAllForRestaurantId(int restaurantId) {
        return dayMenuRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public List<DayMenu> getAllForDayByRestaurantId(int restaurantId, LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return dayMenuRepository.getAllForDateAndRestaurantId(restaurantId, date);
    }

    @Override
    public List<DayMenu> getAllForDay(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return dayMenuRepository.getAllForDate(date);
    }

    @Override
    public DayMenu get(int id, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(dayMenuRepository.get(id, restaurantId), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void delete(int id, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(dayMenuRepository.delete(id, restaurantId) != 0, id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void deleteAllForDay(int restaurantId, LocalDate date) {
        checkNotFoundWithId(dayMenuRepository.deleteAll(restaurantId, date) != 0, restaurantId);
    }
}
