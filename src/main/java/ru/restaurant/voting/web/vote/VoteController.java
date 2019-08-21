package ru.restaurant.voting.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.service.vote.VoteService;

import java.time.LocalDate;
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

    @GetMapping()
    public List<Vote> getAllForToday() {
        log.info("get all votes for today");
        return voteService.getAllForDate(null);
    }

    @GetMapping("/for")
    public List<Vote> getAllForDate(@RequestParam LocalDate day) {
        log.info("get all votes for date");
        return voteService.getAllForDate(day);
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote with id={}", id);
        return voteService.get(id);
    }
}
