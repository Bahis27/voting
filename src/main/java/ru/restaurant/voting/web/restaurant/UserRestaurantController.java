package ru.restaurant.voting.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant.voting.AuthorizedUser;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.to.RestaurantToWithStats;

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

    @Override
    @GetMapping
    public List<Restaurant> getAllForToday() {
        return super.getAllForToday();
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<Vote> vote(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser user) {
        Vote vote = super.vote(null, user.getId(), id, null);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}/vote")
                .buildAndExpand(vote.getRestaurantId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(vote);
    }

    @GetMapping("/{id}/stat")
    public int getStatForDay(@PathVariable int id) {
        return super.getStatForDay(null, id);
    }

    @GetMapping("/stat")
    public List<RestaurantToWithStats> getAllWithStatForDay() {
        return super.getAllWithStatForDay(null);
    }

    @Override
    @GetMapping("/{id}/dishes")
    public List<Dish> getAllDishesForRestaurant(@PathVariable int id) {
        return super.getAllDishesForRestaurant(id);
    }

    @GetMapping("/{id}/menus")
    public List<DayMenu> getAllDayMenusForDay(@PathVariable int id) {
        return super.getAllDayMenusForDayByRestaurantId(id, null);
    }

    @GetMapping("/menus")
    public List<DayMenu> getAllDayMenusForDay() {
        return super.getAllDayMenusForDay(null);
    }
}
