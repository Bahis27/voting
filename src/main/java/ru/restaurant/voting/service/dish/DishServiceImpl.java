package ru.restaurant.voting.service.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.repository.DishRepository;
import ru.restaurant.voting.service.restaurant.RestaurantService;
import ru.restaurant.voting.to.DishTo;
import ru.restaurant.voting.util.ToUtil;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.util.List;

import static ru.restaurant.voting.util.ValidationUtil.checkNew;
import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final RestaurantService restaurantService;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository, RestaurantService restaurantService) {
        this.dishRepository = dishRepository;
        this.restaurantService = restaurantService;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    @Override
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNew(dish);
        dish.setRestaurant(restaurantService.get(restaurantId));
        return dishRepository.save(dish);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    @Override
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        Dish loadedDish = dishRepository.get(dish.getId(), restaurantId);
        checkNotFoundWithId(loadedDish, dish.getId());

        dish.setRestaurant(loadedDish.getRestaurant());
        dishRepository.save(dish);
    }

    @Override
    public List<DishTo> getAll() {
        return ToUtil.dishesAsToList(dishRepository.getAll());
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public Dish get(int id, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(dishRepository.get(id, restaurantId), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void delete(int id, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(dishRepository.delete(id, restaurantId) != 0, id);
    }
}
