package ru.restaurant.voting.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.repository.RestaurantRepository;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.util.RestaurantUtil;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.restaurant.voting.util.RestaurantUtil.*;
import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void update(RestaurantTo restaurantTo) {
        Assert.notNull(restaurantTo, "restaurant must not be null");
        checkNotFoundWithId(restaurantTo, restaurantTo.getId());
        restaurantRepository.save(asEntity(restaurantTo));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.deleteWithId(id), id);
    }

    @Override
    public RestaurantTo get(int id) throws NotFoundException {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
        return asTo(Objects.requireNonNull(restaurant));
    }

    @Override
    public List<RestaurantTo> getAll() {
        return restaurantRepository.getAll().stream()
                .map(RestaurantUtil::asTo)
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant getForDay(int id, LocalDate localDate) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.getByIdForDay(id, Objects.requireNonNullElseGet(localDate, LocalDate::now)), id);
    }

    @Override
    public List<Restaurant> getAllForDay(LocalDate localDate) {
        return restaurantRepository.getAllForDay(Objects.requireNonNullElseGet(localDate, LocalDate::now));
    }
}