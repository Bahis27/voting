package ru.restaurant.voting.service.vote;

import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface VoteService {

    List<VoteTo> getAllForDate(LocalDate date);

    Vote get(int id);

    Optional<Vote> getByUserIdAndVotingDate(int userId, LocalDate votingDay);

    Vote vote(LocalDate date, int userId, int restaurantId, LocalTime time);

    List<VoteTo> getAllForDateForUser(LocalDate date, int userId);

    List<VoteTo> getAllForDateForRestaurant(LocalDate date, int restaurantId);
}
