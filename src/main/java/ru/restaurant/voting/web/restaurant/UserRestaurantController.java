package ru.restaurant.voting.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant.voting.AuthorizedUser;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.to.VoteTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {
    public static final String REST_URL = "/restaurants";

    @GetMapping("/{id}")
    public Restaurant getForDay(@PathVariable int id) {
        return super.getForDay(id, LocalDate.now());
    }

    @Override
    @GetMapping
    public List<Restaurant> getAllForToday() {
        return super.getAllForToday();
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<Vote> vote(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser user) {
        Vote vote = super.vote(LocalDate.now(), user.getId(), id, LocalTime.now());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}/vote")
                .buildAndExpand(vote.getRestaurant().getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(vote);
    }

    @GetMapping("/{id}/menus")
    public List<DayMenu> getAllDayMenusForDay(@PathVariable int id) {
        return super.getAllDayMenusForDayByRestaurantId(id, LocalDate.now());
    }

    @GetMapping("/menus")
    public List<DayMenu> getAllDayMenusForDay() {
        return super.getAllDayMenusForDay(LocalDate.now());
    }

    @GetMapping("/votes")
    public List<VoteTo> getAllVotesForDay(@AuthenticationPrincipal AuthorizedUser user) {
        return super.getAllForDateForUser(LocalDate.now(), user.getId());
    }
}
