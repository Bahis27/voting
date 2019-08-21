package ru.restaurant.voting.service.vote;

import ru.restaurant.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {

    List<Vote> getAllForDate(LocalDate date);

    Vote get(int id);
}
