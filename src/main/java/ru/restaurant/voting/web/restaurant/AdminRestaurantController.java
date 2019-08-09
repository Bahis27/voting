package ru.restaurant.voting.web.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant.voting.View;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.to.RestaurantTo;
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
    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
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

    //statistics
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

    //dishes
    @Override
    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dish createDish(@Validated(View.Web.class) @RequestBody Dish dish, @PathVariable int restaurantId) {
        return super.createDish(dish, restaurantId);
    }

    @Override
    @PutMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDish(@Validated(View.Web.class) @RequestBody Dish dish, @PathVariable int restaurantId) {
        super.updateDish(dish, restaurantId);
    }

    @Override
    @GetMapping("/{restaurantId}/dishes")
    public List<Dish> getAllDishes(@PathVariable int restaurantId) {
        return super.getAllDishes(restaurantId);
    }

    @Override
    @GetMapping("/{restaurantId}/dishes/{id}")
    public Dish getDish(@PathVariable int id, @PathVariable int restaurantId) {
        return super.getDish(id, restaurantId);
    }

    @Override
    @DeleteMapping("/{restaurantId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int id, @PathVariable int restaurantId) {
        super.deleteDish(id, restaurantId);
    }

    //dayMenus
    @Override
    @PostMapping(value = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DayMenu createDayMenu(@Validated(View.Web.class) @RequestBody DayMenu dayMenu, @PathVariable int restaurantId) {
        return super.createDayMenu(dayMenu, restaurantId);
    }

    @Override
    @PutMapping(value = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDayMenu(@Validated(View.Web.class) @RequestBody DayMenu dayMenu, @PathVariable int restaurantId) {
        super.updateDayMenu(dayMenu, restaurantId);
    }

    @Override
    @GetMapping("/{restaurantId}/menus")
    public List<DayMenu> getAllDayMenus(@PathVariable int restaurantId) {
        return super.getAllDayMenus(restaurantId);
    }

    @Override
    @GetMapping("/{restaurantId}/menus/for")
    public List<DayMenu> getAllDayMenusForDay(@PathVariable int restaurantId, @RequestParam LocalDate day) {
        return super.getAllDayMenusForDay(restaurantId, day);
    }

    @Override
    @GetMapping("/{restaurantId}/menus/{id}")
    public DayMenu getDayMenu(@PathVariable int id, @PathVariable int restaurantId) {
        return super.getDayMenu(id, restaurantId);
    }

    @Override
    @DeleteMapping("/{restaurantId}/menus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDayMenu(@PathVariable int id, @PathVariable int restaurantId) {
        super.deleteDayMenu(id, restaurantId);
    }

    @Override
    @DeleteMapping("/{restaurantId}/menus/for")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllDayMenusForDay(@PathVariable int restaurantId, @RequestParam LocalDate day) {
        super.deleteAllDayMenusForDay(restaurantId, day);
    }
}
