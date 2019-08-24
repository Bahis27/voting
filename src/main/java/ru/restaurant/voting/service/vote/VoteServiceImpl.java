package ru.restaurant.voting.service.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.repository.VoteRepository;
import ru.restaurant.voting.util.exception.UserAlreadyHasVotedExceptionBefore11am;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public List<Vote> getAllForDate(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return voteRepository.getAllForDate(date);
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
        if (time == null) {
            time = LocalTime.now();
        }
        if (date == null) {
            date = LocalDate.now();
        }
        Optional<Vote> optionalVote = voteRepository.findByUserIdAndVotingDate(userId, date);
        if (optionalVote.isEmpty()) {
            return voteRepository.save(new Vote(null, date, userId, restaurantId));
        } else {
            Vote vote = null;
            if (time.isBefore(LocalTime.of(11, 0, 0))) {
                vote = voteRepository.save(new Vote(optionalVote.get().getId(), date, userId, restaurantId));
            } else {
                throw new UserAlreadyHasVotedExceptionBefore11am(String.format("User with userId = %d already has voted before 11.00 a.m.", userId));
            }
            return vote;
        }
    }
}
