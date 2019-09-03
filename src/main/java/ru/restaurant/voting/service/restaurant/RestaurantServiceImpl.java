package ru.restaurant.voting.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.repository.RestaurantRepository;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.util.ToUtil;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static ru.restaurant.voting.util.ValidationUtil.checkNew;
import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurant, restaurant.getId());
        restaurantRepository.save(restaurant);
    }

    @Override
    public List<RestaurantTo> getAll() {
        return ToUtil.restaurantsAsToList(restaurantRepository.getAll());
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        Restaurant restaurant = restaurantRepository.getFullById(id);
        if (restaurant == null) {
            return restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));
        }
        return checkNotFoundWithId(restaurant, id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    @Override
    public Restaurant getForDay(int id, LocalDate localDate) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.getByIdForDay(id, localDate), id);
    }

    @Override
    public List<Restaurant> getAllForDay(LocalDate localDate) {
        return restaurantRepository.getAllForDay(localDate);
    }

    @Override
    @Cacheable("restaurants")
    public List<Restaurant> getAllForToday() {
        return restaurantRepository.getAllForDay(LocalDate.now());
    }
}