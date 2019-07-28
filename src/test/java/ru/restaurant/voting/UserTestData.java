package ru.restaurant.voting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.restaurant.voting.model.Role;
import ru.restaurant.voting.model.User;
import ru.restaurant.voting.web.json.JsonUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.restaurant.voting.TestUtil.readFromJsonMvcResult;
import static ru.restaurant.voting.TestUtil.readListFromJsonMvcResult;

public class UserTestData {
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

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
