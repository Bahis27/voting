package ru.restaurant.voting.util;

import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.to.RestaurantTo;

public class RestaurantUtil {

    public static RestaurantTo asTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }

    public static Restaurant asEntity(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }
}
