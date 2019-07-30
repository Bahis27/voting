package ru.restaurant.voting.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.restaurant.voting.AuthorizedUser;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {
    public static final String REST_URL = "/restaurants";

    @GetMapping("/{id}")
    public Restaurant getForDay(@PathVariable int id) {
        return super.getForDay(id, null);
    }

    @GetMapping
    public List<Restaurant> getAllForDay() {
        return super.getAllForDay(null);
    }

    @PostMapping("/vote")
    public Vote vote(@RequestParam int id, @AuthenticationPrincipal AuthorizedUser user) {
        return super.vote(LocalDate.now(), user.getId(), id);
    }
}
