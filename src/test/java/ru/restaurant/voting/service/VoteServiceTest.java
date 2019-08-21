package ru.restaurant.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.service.vote.VoteService;

import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.voting.TestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    VoteService voteService;

    @Test
    void getAll() throws Exception {
        List<Vote> all = voteService.getAllForDate(LocalDate.of(2019, 7, 3));
        assertMatch(all, VOTES_FOR_20190713);
    }

    @Test
    void get() throws Exception {
        Vote vote = voteService.get(VOTE16_ID);
        assertMatch(vote, VOTE16);
    }
}
