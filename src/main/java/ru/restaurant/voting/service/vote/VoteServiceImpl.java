package ru.restaurant.voting.service.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.User;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.repository.RestaurantRepository;
import ru.restaurant.voting.repository.UserRepository;
import ru.restaurant.voting.repository.VoteRepository;
import ru.restaurant.voting.to.VoteTo;
import ru.restaurant.voting.util.ToUtil;
import ru.restaurant.voting.util.exception.UserAlreadyHasVotedExceptionBefore11am;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<VoteTo> getAllForDate(LocalDate date) {
        return ToUtil.votesAsToList(voteRepository.getAllForDate(date));
    }

    @Override
    public Vote get(int id) {
        return voteRepository.get(id);
    }

    @Override
    public Optional<Vote> getByUserIdAndVotingDate(int userId, LocalDate votingDay) {
        return voteRepository.findByUserIdAndVotingDate(userId, votingDay);
    }

    @Transactional
    @Override
    public Vote vote(LocalDate date, int userId, int restaurantId, LocalTime time) {

        Optional<Vote> optionalVote = voteRepository.findByUserIdAndVotingDate(userId, date);
        User user = userRepository.getOne(userId);
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);

        if (optionalVote.isEmpty()) {
            return voteRepository.save(new Vote(null, date, user, restaurant));
        } else {
            Vote vote = null;
            if (time.isBefore(LocalTime.of(11, 0, 0))) {
                vote = voteRepository.save(new Vote(optionalVote.get().getId(), date, user, restaurant));
            } else {
                throw new UserAlreadyHasVotedExceptionBefore11am(String.format("User with userId = %d already has voted before 11.00 a.m.", userId));
            }
            return vote;
        }
    }

    @Override
    public List<VoteTo> getAllForDateForUser(LocalDate date, int userId) {
        return ToUtil.votesAsToList(voteRepository.getAllForDateForUser(date, userId));
    }

    @Override
    public List<VoteTo> getAllForDateForRestaurant(LocalDate date, int restaurantId) {
        return ToUtil.votesAsToList(voteRepository.getAllForDateForRestaurant(date, restaurantId));
    }
}
