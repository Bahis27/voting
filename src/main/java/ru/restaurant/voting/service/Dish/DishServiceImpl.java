package ru.restaurant.voting.service.Dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.repository.DishRepository;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.util.List;

import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return dishRepository.save(dish);
    }

    @Override
    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dish, dish.getId());
        dishRepository.save(dish);
    }

    @Override
    public void delete(int id, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(dishRepository.delete(id, restaurantId), id);
    }

    @Override
    public Dish get(int id, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(dishRepository.get(id, restaurantId), id);
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAllDishesByRestaurantId(restaurantId);
    }
}
