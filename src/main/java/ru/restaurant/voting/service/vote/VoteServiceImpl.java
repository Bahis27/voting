package ru.restaurant.voting.service.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

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
}
