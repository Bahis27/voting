package ru.restaurant.voting.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.repository.RestaurantRepository;
import ru.restaurant.voting.repository.VoteRepository;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.util.exception.NotFoundException;
import ru.restaurant.voting.util.exception.UserAlreadyHasVotedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurant, restaurant.getId());
        restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.deleteWithId(id), id);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        Restaurant restaurant = restaurantRepository.getFullById(id);
        if (restaurant == null) {
            return checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
        }
        return checkNotFoundWithId(restaurant, id);
    }

    @Override
    public List<RestaurantTo> getAll() {
        return restaurantRepository.getAll().stream()
                .map(this::asTo)
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

    @Override
    public Vote vote(LocalDate date, int userId, int restaurantId) {
        Optional<Vote> optionalVote = voteRepository.findByUserIdAndVotingDate(userId, date);
        if (optionalVote.isEmpty()) {
            return voteRepository.save(new Vote(date, userId, restaurantId));
        } else {
            Vote vote = null;
            if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
                voteRepository.deleteByUserIdAndVotingDate(userId, date);
                vote = voteRepository.save(new Vote(date, userId, restaurantId));
            } else {
                throw new UserAlreadyHasVotedException(String.format("User with userId = %d already has voted", userId));
            }
            return vote;
        }
    }

    private RestaurantTo asTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }
}