package ru.restaurant.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.service.vote.VoteService;

import java.util.List;

import static ru.restaurant.voting.TestData.*;

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
}
