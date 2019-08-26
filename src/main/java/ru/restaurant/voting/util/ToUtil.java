package ru.restaurant.voting.util;

import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Vote;
import ru.restaurant.voting.to.DayMenuTO;
import ru.restaurant.voting.to.DishTo;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

public class ToUtil {

    public static List<RestaurantTo> restaurantsAsToList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantTo::new)
                .collect(Collectors.toList());
    }

    public static List<DishTo> dishesAsToList(List<Dish> dishes) {
        return dishes.stream()
                .map(DishTo::new)
                .collect(Collectors.toList());
    }

    public static List<DayMenuTO> dayMenusAsToList(List<DayMenu> dayMenus) {
        return dayMenus.stream()
                .map(DayMenuTO::new)
                .collect(Collectors.toList());
    }

    public static List<VoteTo> votesAsToList(List<Vote> votes) {
        return votes.stream()
                .map(VoteTo::new)
                .collect(Collectors.toList());
    }
}
