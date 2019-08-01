package ru.restaurant.voting.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant.voting.AuthorizedUser;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;

import java.net.URI;
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

    @PostMapping("/vote/{id}")
    public ResponseEntity<Vote> vote(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser user) {
        Vote vote = super.vote(null, user.getId(), id, null);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/vote/{id}")
                .buildAndExpand(vote.getRestaurantId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(vote);
    }

    @Override
    @GetMapping("/{id}/all")
    public List<Dish> getAllDishes(@PathVariable int id) {
        return super.getAllDishes(id);
    }
}
