package ru.restaurant.voting.web.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant.voting.View;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.to.RestaurantNamesTo;
import ru.restaurant.voting.to.RestaurantToWithStats;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/admin/restaurants";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated(View.Web.class) @RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Restaurant restaurant) {
        super.update(restaurant);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<RestaurantNamesTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}/for")
    public Restaurant getForDay(@PathVariable int id, @RequestParam LocalDate day) {
        return super.getForDay(id, day);
    }

    @Override
    @GetMapping("/for")
    public List<Restaurant> getAllForDay(@RequestParam LocalDate day) {
        return super.getAllForDay(day);
    }

    @Override
    @GetMapping("/{id}/stat/for")
    public int getStatForDay(@RequestParam LocalDate day, @PathVariable int id) {
        return super.getStatForDay(day, id);
    }

    @Override
    @GetMapping("/{id}/stat")
    public int getStat(@PathVariable int id) {
        return super.getStat(id);
    }

    @Override
    @GetMapping("/stat")
    public List<RestaurantToWithStats> getAllWithStat() {
        return super.getAllWithStat();
    }

    @Override
    @GetMapping("/stat/for")
    public List<RestaurantToWithStats> getAllWithStatForDay(@RequestParam LocalDate day) {
        return super.getAllWithStatForDay(day);
    }
}
