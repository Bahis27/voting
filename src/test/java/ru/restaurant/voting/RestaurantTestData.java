package ru.restaurant.voting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final int START_RESTAURANTS_SEQ = 100;
    public static final int RES1_ID = START_RESTAURANTS_SEQ + 1;
    public static final int RES2_ID = START_RESTAURANTS_SEQ + 2;
    public static final int RES3_ID = START_RESTAURANTS_SEQ + 3;
    public static final int RES4_ID = START_RESTAURANTS_SEQ + 4;
    public static final int RES5_ID = START_RESTAURANTS_SEQ + 5;
    public static final int RES6_ID = START_RESTAURANTS_SEQ + 6;
    public static final int RES7_ID = START_RESTAURANTS_SEQ + 7;
    public static final int RES8_ID = START_RESTAURANTS_SEQ + 8;
    public static final int RES9_ID = START_RESTAURANTS_SEQ + 9;

    public static final Restaurant RES1 = new Restaurant(RES1_ID, "Kale Me Crazy");
    public static final Restaurant RES2 = new Restaurant(RES2_ID, "Wok And Roll");
    public static final Restaurant RES3 = new Restaurant(RES3_ID, "Life of Pie");
    public static final Restaurant RES4 = new Restaurant(RES4_ID, "Lord of the Fries");
    public static final Restaurant RES5 = new Restaurant(RES5_ID, "Pita Pan");
    public static final Restaurant RES6 = new Restaurant(RES6_ID, "Why Not Bar");
    public static final Restaurant RES7 = new Restaurant(RES7_ID, "Planet of the Crepes");
    public static final Restaurant RES8 = new Restaurant(RES8_ID, "Thai Tanic");
    public static final Restaurant RES9 = new Restaurant(RES9_ID, "He is Not Here Bar");

    public static final List<Dish> RES3_DISHES = List.of(
            new Dish(1007, "Meet Pie", 350, RES3),
            new Dish(1008,"Sweet Pie",320, RES3),
            new Dish(1009,"Just Pie",300, RES3)
    );

    public static final List<Restaurant> RESTAURANTS = List.of(RES1, RES2, RES3, RES4, RES5, RES6, RES7, RES8, RES9);

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dayMenus");
    }

    public static void assertMatch(List<RestaurantTo> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(List<RestaurantTo> actual, List<Restaurant> expected) {
        List<Restaurant> restaurants = toEntityList(actual);
        assertThat(restaurants).usingElementComparatorIgnoringFields("dayMenus").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, List<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dayMenus").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Iterable<RestaurantTo> expected) {
        return result -> assertThat(TestUtil.readListFromJsonMvcResult(result, RestaurantTo.class)).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(List<Restaurant> expected) {
        return result -> assertThat(TestUtil.readListFromJsonMvcResult(result, Restaurant.class)).isEqualTo(expected);
    }

    public static List<Restaurant> toEntityList(List<RestaurantTo> restaurantTos) {
        return restaurantTos.stream()
                .map(Restaurant::new)
                .collect(Collectors.toList());
    }

    public static List<RestaurantTo> toToList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantTo::new)
                .collect(Collectors.toList());
    }
}
