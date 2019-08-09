package ru.restaurant.voting.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.repository.DayMenuRepository;
import ru.restaurant.voting.repository.DishRepository;
import ru.restaurant.voting.repository.RestaurantRepository;
import ru.restaurant.voting.repository.VoteRepository;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.to.RestaurantToWithStats;
import ru.restaurant.voting.util.exception.NotFoundException;
import ru.restaurant.voting.util.exception.RestaurantHasNotMenuForThisDay;
import ru.restaurant.voting.util.exception.UserAlreadyHasVotedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;
import static ru.restaurant.voting.util.ValidationUtil.checkNew;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final DishRepository dishRepository;
    private final DayMenuRepository dayMenuRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 VoteRepository voteRepository,
                                 DishRepository dishRepository,
                                 DayMenuRepository dayMenuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
        this.dishRepository = dishRepository;
        this.dayMenuRepository = dayMenuRepository;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurant, restaurant.getId());
        restaurantRepository.save(restaurant);
    }

    @Override
    public List<RestaurantTo> getAll() {
        return restaurantRepository.getAll().stream()
                .map(this::asTo)
                .collect(Collectors.toList());
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
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    @Override
    public Restaurant getForDay(int id, LocalDate localDate) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.getByIdForDay(id, Objects.requireNonNullElseGet(localDate, LocalDate::now)), id);
    }

    @Override
    public List<Restaurant> getAllForDay(LocalDate localDate) {
        return restaurantRepository.getAllForDay(Objects.requireNonNullElseGet(localDate, LocalDate::now));
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
            return voteRepository.save(new Vote(date, userId, restaurantId));
        } else {
            Vote vote = null;
            if (time.isBefore(LocalTime.of(11, 0, 0))) {
                voteRepository.deleteByUserIdAndVotingDate(userId, date);
                vote = voteRepository.save(new Vote(date, userId, restaurantId));
            } else {
                throw new UserAlreadyHasVotedException(String.format("User with userId = %d already has voted", userId));
            }
            return vote;
        }
    }

    @Override
    public int getStatForDay(LocalDate date, int restaurantId) {
        if (date == null) {
            date = LocalDate.now();
        }
        return voteRepository.getAllForDate(date, restaurantId).size();
    }

    @Override
    public int getStat(int restaurantId) {
        return voteRepository.getAll(restaurantId).size();
    }

    @Transactional
    @Override
    public List<RestaurantToWithStats> getAllWithStatsForDay(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        List<RestaurantToWithStats> all = getAllForDay(date).stream()
                .map(RestaurantToWithStats::new)
                .collect(Collectors.toList());
        LocalDate finalDate = date;
        all.forEach(r -> r.setStat(getStatForDay(finalDate, r.getId())));
        sortByStatAndName(all);
        return all;
    }

    @Transactional
    @Override
    public List<RestaurantToWithStats> getAllWithStats() {
        List<RestaurantToWithStats> all = getAll().stream()
                .map(RestaurantToWithStats::new)
                .collect(Collectors.toList());
        all.forEach(r -> r.setStat(getStat(r.getId())));
        sortByStatAndName(all);
        return all;
    }

    private void sortByStatAndName(List<RestaurantToWithStats> all) {
        all.sort(Comparator
                .comparing(RestaurantToWithStats::getStat)
                .reversed()
                .thenComparing(RestaurantToWithStats::getName));
    }

    private RestaurantTo asTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }
}