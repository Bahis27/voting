package ru.restaurant.voting.service.vote;

import ru.restaurant.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {

    List<Vote> getAll();

    Vote get(int id);

    List<Vote> getAllForDate(LocalDate date);

    List<Vote> getAllForDateForUser(LocalDate date, int userId);

    List<Vote> getAllForUser(int userId);
}
