package ru.restaurant.voting.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.service.vote.VoteService;

import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    public static final String REST_URL = "/admin/votes";

    @GetMapping
    public List<Vote> getAll() {
        log.info("get all votes");
        return voteService.getAll();
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote with id={}", id);
        return voteService.get(id);
    }
}
