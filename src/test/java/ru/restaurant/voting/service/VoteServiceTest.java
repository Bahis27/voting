package ru.restaurant.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.service.vote.VoteService;

import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.voting.TestData.*;
import static ru.restaurant.voting.TestData.assertMatch;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    VoteService voteService;

    @Test
    void getAll() throws Exception {
        List<Vote> all = voteService.getAll();
        assertMatch(all, VOTES);
    }

    @Test
    void get() throws Exception {
        Vote vote = voteService.get(VOTE16_ID);
        assertMatch(vote, VOTE16);
    }

    @Test
    void getAllForDate() throws Exception {
        List<Vote> votes = voteService.getAllForDate(LocalDate.of(2019, 7, 3));
        assertMatch(votes, VOTES_FOR_20190703);
    }

    @Test
    void getAllForDateForUser() throws Exception {
        List<Vote> votes = voteService.getAllForDateForUser(LocalDate.of(2019, 7, 3), USER4_ID);
        assertMatch(votes, VOTES_FOR_20190703_AND_USER4);
    }

    @Test
    void getAllForUser() throws Exception {
        List<Vote> votes = voteService.getAllForUser(USER4_ID);
        assertMatch(votes, VOTES_FOR_USER4);
    }
}
