package ru.restaurant.voting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;
import ru.restaurant.voting.model.Role;
import ru.restaurant.voting.model.User;
import ru.restaurant.voting.to.RestaurantTo;
import ru.restaurant.voting.web.json.JsonUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.restaurant.voting.TestUtil.readFromJsonMvcResult;
import static ru.restaurant.voting.TestUtil.readListFromJsonMvcResult;

public class TestData {
    public static final int START_USERS_SEQ = 10;

    public static final int ADMIN_ID = START_USERS_SEQ;
    public static final int USER1_ID = START_USERS_SEQ + 1;
    public static final int USER2_ID = START_USERS_SEQ + 2;
    public static final int USER3_ID = START_USERS_SEQ + 3;
    public static final int USER4_ID = START_USERS_SEQ + 4;
    public static final int USER5_ID = START_USERS_SEQ + 5;
    public static final int USER6_ID = START_USERS_SEQ + 6;
    public static final int USER7_ID = START_USERS_SEQ + 7;
    public static final int USER8_ID = START_USERS_SEQ + 8;

    public static final User ADMIN = new User(ADMIN_ID, "admin", "admin@gmail.com", "password", Role.ROLE_ADMIN, Role.ROLE_USER);
    public static final User USER1 = new User(USER1_ID, "hotab", "hotab58@yandex.ru", "oVolka", Role.ROLE_USER);
    public static final User USER2 = new User(USER2_ID, "volk", "volk27@mail.ru", "NuZayatsPogodi", Role.ROLE_USER);
    public static final User USER3 = new User(USER3_ID, "ninja", "shadowninja@gmail.com", "youwillnotseeme", Role.ROLE_USER);
    public static final User USER4 = new User(USER4_ID, "hotgirl", "hotgirl69@loveplanet.net", "NoMoneyNoHoney", Role.ROLE_USER);
    public static final User USER5 = new User(USER5_ID, "batman", "batmanofghotem@yandex.ru", "ImBatman", Role.ROLE_USER);
    public static final User USER6 = new User(USER6_ID, "spiderman", "friendlyspidey@gmail.com", "thegreatpoweristhegreatresponsibility", Role.ROLE_USER);
    public static final User USER7 = new User(USER7_ID, "pussinboots", "pussinboots@mail.ru","123myau123", Role.ROLE_USER);
    public static final User USER8 = new User(USER8_ID, "simple", "simple@mail.ru", "simple", Role.ROLE_USER);

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

    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    public static <T> void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static <T> void assertMatch(T actual, T expected, String... ignore) {
        assertThat(actual).usingComparatorForFields((x, y) -> 0, ignore).isEqualToComparingFieldByFieldRecursively(expected);
    }

    public static <T> ResultMatcher contentJson(Class clazz, T... expected) {
        return contentJson(clazz, List.of(expected));
    }

    public static <T> ResultMatcher contentJson(Class clazz, Iterable<T> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, clazz), expected);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
