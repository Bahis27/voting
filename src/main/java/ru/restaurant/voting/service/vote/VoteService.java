package ru.restaurant.voting.service.vote;

import ru.restaurant.voting.model.Vote;

import java.util.List;

public interface VoteService {

    List<Vote> getAll();

    Vote get(int id);
}
