package ru.restaurant.voting.service.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.repository.DishRepository;
import ru.restaurant.voting.repository.RestaurantRepository;
import ru.restaurant.voting.to.DishTo;
import ru.restaurant.voting.util.ToUtil;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.util.List;

import static ru.restaurant.voting.util.ValidationUtil.checkNew;
import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    @Override
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        return dishRepository.save(dish);
    }

    @Transactional
    @Override
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dishRepository.get(dish.getId(), restaurantId), dish.getId());
        dish.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
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

    @Override
    public void delete(int id, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(dishRepository.delete(id, restaurantId), id);
    }
}
