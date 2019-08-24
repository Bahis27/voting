package ru.restaurant.voting.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.repository.DayMenuRepository;
import ru.restaurant.voting.repository.RestaurantRepository;
import ru.restaurant.voting.repository.VoteRepository;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.util.ToUtil;
import ru.restaurant.voting.util.exception.NotFoundException;
import ru.restaurant.voting.util.exception.RestaurantHasNotMenuForThisDay;
import ru.restaurant.voting.util.exception.UserAlreadyHasVotedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.restaurant.voting.util.ValidationUtil.checkNew;
import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final DayMenuRepository dayMenuRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 VoteRepository voteRepository,
                                 DayMenuRepository dayMenuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
        this.dayMenuRepository = dayMenuRepository;
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
        return checkNotFoundWithId(restaurantRepository.getByIdForDay(id, Objects.requireNonNullElseGet(localDate, LocalDate::now)), id);
    }

    @Override
    public List<Restaurant> getAllForDay(LocalDate localDate) {
        return restaurantRepository.getAllForDay(Objects.requireNonNullElseGet(localDate, LocalDate::now));
    }

    @Override
    @Cacheable("restaurants")
    public List<Restaurant> getAllForToday() {
        return restaurantRepository.getAllForDay(LocalDate.now());
    }

    @Transactional
    @Override
    public Vote vote(LocalDate date, int userId, int restaurantId, LocalTime time) {
        if (time == null) {
            time = LocalTime.now();
        }
        if (date == null) {
            date = LocalDate.now();
        }
        if (dayMenuRepository.getAllForDateAndRestaurantId(restaurantId, date).isEmpty()) {
            throw new RestaurantHasNotMenuForThisDay(String.format("Restaurant with restaurantId = %d has not DayMenu for this day", restaurantId));
        }
        Optional<Vote> optionalVote = voteRepository.findByUserIdAndVotingDate(userId, date);
        if (optionalVote.isEmpty()) {
            return voteRepository.save(new Vote(null, date, userId, restaurantId));
        } else {
            Vote vote = null;
            if (time.isBefore(LocalTime.of(11, 0, 0))) {
                vote = voteRepository.save(new Vote(optionalVote.get().getId(), date, userId, restaurantId));
            } else {
                throw new UserAlreadyHasVotedException(String.format("User with userId = %d already has voted", userId));
            }
            return vote;
        }
    }
}