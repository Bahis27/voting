package ru.restaurant.voting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.restaurant.voting.model.*;
import ru.restaurant.voting.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    public static final User USER7 = new User(USER7_ID, "pussinboots", "pussinboots@mail.ru", "123myau123", Role.ROLE_USER);
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

    public static final List<Restaurant> RESTAURANTS = List.of(RES1, RES2, RES3, RES4, RES5, RES6, RES7, RES8, RES9);
    public static final List<Restaurant> RESTAURANTS_FOR_DAY_20190701 = List.of(RES9, RES1, RES3, RES4, RES5, RES7, RES2);
    public static final List<Restaurant> RESTAURANTS_FOR_DAY_20190701_WITHOUT_RES1 = List.of(RES9, RES3, RES4, RES5, RES7, RES2);

    public static final int START_DISHES_SEQ = 1000;
    public static final int DISH1_ID = START_DISHES_SEQ + 1;
    public static final int DISH2_ID = START_DISHES_SEQ + 2;
    public static final int DISH3_ID = START_DISHES_SEQ + 3;
    public static final int DISH4_ID = START_DISHES_SEQ + 4;
    public static final int DISH5_ID = START_DISHES_SEQ + 5;
    public static final int DISH6_ID = START_DISHES_SEQ + 6;
    public static final int DISH7_ID = START_DISHES_SEQ + 7;
    public static final int DISH8_ID = START_DISHES_SEQ + 8;
    public static final int DISH9_ID = START_DISHES_SEQ + 9;
    public static final int DISH10_ID = START_DISHES_SEQ + 10;
    public static final int DISH11_ID = START_DISHES_SEQ + 11;
    public static final int DISH12_ID = START_DISHES_SEQ + 12;
    public static final int DISH13_ID = START_DISHES_SEQ + 13;
    public static final int DISH14_ID = START_DISHES_SEQ + 14;
    public static final int DISH15_ID = START_DISHES_SEQ + 15;
    public static final int DISH16_ID = START_DISHES_SEQ + 16;
    public static final int DISH17_ID = START_DISHES_SEQ + 17;
    public static final int DISH18_ID = START_DISHES_SEQ + 18;
    public static final int DISH19_ID = START_DISHES_SEQ + 19;
    public static final int DISH20_ID = START_DISHES_SEQ + 20;
    public static final int DISH21_ID = START_DISHES_SEQ + 21;
    public static final int DISH22_ID = START_DISHES_SEQ + 22;
    public static final int DISH23_ID = START_DISHES_SEQ + 23;
    public static final int DISH24_ID = START_DISHES_SEQ + 24;
    public static final int DISH25_ID = START_DISHES_SEQ + 25;
    public static final int DISH26_ID = START_DISHES_SEQ + 26;
    public static final int DISH27_ID = START_DISHES_SEQ + 27;

    public static final Dish DISH1 = new Dish(DISH1_ID, "French Fries", RES1);
    public static final Dish DISH2 = new Dish(DISH2_ID, "Beef Burger", RES1);
    public static final Dish DISH3 = new Dish(DISH3_ID, "Chicken Wings", RES1);
    public static final Dish DISH4 = new Dish(DISH4_ID, "Signature Wok", RES2);
    public static final Dish DISH5 = new Dish(DISH5_ID, "Yakisoba Noodles", RES2);
    public static final Dish DISH6 = new Dish(DISH6_ID, "Salmon Rolls", RES2);
    public static final Dish DISH7 = new Dish(DISH7_ID, "Meet Pie", RES3);
    public static final Dish DISH8 = new Dish(DISH8_ID, "Sweet Pie", RES3);
    public static final Dish DISH9 = new Dish(DISH9_ID, "Just Pie", RES3);
    public static final Dish DISH10 = new Dish(DISH10_ID, "Greek Chicken Pasta", RES4);
    public static final Dish DISH11 = new Dish(DISH11_ID, "Tuscan Bean Soup", RES4);
    public static final Dish DISH12 = new Dish(DISH12_ID, "French Fries", RES4);
    public static final Dish DISH13 = new Dish(DISH13_ID, "Big Beer", RES5);
    public static final Dish DISH14 = new Dish(DISH14_ID, "French Fries", RES5);
    public static final Dish DISH15 = new Dish(DISH15_ID, "Spicy Basque Chicken and Rice", RES5);
    public static final Dish DISH16 = new Dish(DISH16_ID, "French Fries", RES6);
    public static final Dish DISH17 = new Dish(DISH17_ID, "Salmon Provencal", RES6);
    public static final Dish DISH18 = new Dish(DISH18_ID, "Moroccan Chicken Skillet", RES6);
    public static final Dish DISH19 = new Dish(DISH19_ID, "French Fries", RES7);
    public static final Dish DISH20 = new Dish(DISH20_ID, "CheeseBurger", RES7);
    public static final Dish DISH21 = new Dish(DISH21_ID, "Pizza 4 Cheeses", RES7);
    public static final Dish DISH22 = new Dish(DISH22_ID, "Thai Sweet Potato Soup", RES8);
    public static final Dish DISH23 = new Dish(DISH23_ID, "Tom Yum Goong", RES8);
    public static final Dish DISH24 = new Dish(DISH24_ID, "Green Curry", RES8);
    public static final Dish DISH25 = new Dish(DISH25_ID, "French Fries", RES9);
    public static final Dish DISH26 = new Dish(DISH26_ID, "Root Beer", RES9);
    public static final Dish DISH27 = new Dish(DISH27_ID, "New York Pizza", RES9);

    public static final List<Dish> RES3_DISHES = List.of(DISH7, DISH8, DISH9);

    public static final List<Dish> DISHES = List.of(DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7,
            DISH8, DISH9, DISH10, DISH11, DISH12, DISH13, DISH14, DISH15, DISH16, DISH17, DISH18,
            DISH19, DISH20, DISH21, DISH22, DISH23, DISH24, DISH25, DISH26, DISH27);

    public static final int START_DAYMENU_SEQ = 10000;
    public static final int DAYMENU1_ID = START_DAYMENU_SEQ + 1;
    public static final int DAYMENU2_ID = START_DAYMENU_SEQ + 2;
    public static final int DAYMENU3_ID = START_DAYMENU_SEQ + 3;
    public static final int DAYMENU4_ID = START_DAYMENU_SEQ + 4;
    public static final int DAYMENU5_ID = START_DAYMENU_SEQ + 5;
    public static final int DAYMENU6_ID = START_DAYMENU_SEQ + 6;
    public static final int DAYMENU7_ID = START_DAYMENU_SEQ + 7;
    public static final int DAYMENU8_ID = START_DAYMENU_SEQ + 8;
    public static final int DAYMENU9_ID = START_DAYMENU_SEQ + 9;
    public static final int DAYMENU10_ID = START_DAYMENU_SEQ + 10;
    public static final int DAYMENU11_ID = START_DAYMENU_SEQ + 11;
    public static final int DAYMENU12_ID = START_DAYMENU_SEQ + 12;
    public static final int DAYMENU13_ID = START_DAYMENU_SEQ + 13;
    public static final int DAYMENU14_ID = START_DAYMENU_SEQ + 14;
    public static final int DAYMENU15_ID = START_DAYMENU_SEQ + 15;
    public static final int DAYMENU16_ID = START_DAYMENU_SEQ + 16;
    public static final int DAYMENU17_ID = START_DAYMENU_SEQ + 17;
    public static final int DAYMENU18_ID = START_DAYMENU_SEQ + 18;
    public static final int DAYMENU19_ID = START_DAYMENU_SEQ + 19;
    public static final int DAYMENU20_ID = START_DAYMENU_SEQ + 20;
    public static final int DAYMENU21_ID = START_DAYMENU_SEQ + 21;
    public static final int DAYMENU22_ID = START_DAYMENU_SEQ + 22;
    public static final int DAYMENU23_ID = START_DAYMENU_SEQ + 23;
    public static final int DAYMENU24_ID = START_DAYMENU_SEQ + 24;
    public static final int DAYMENU25_ID = START_DAYMENU_SEQ + 25;
    public static final int DAYMENU26_ID = START_DAYMENU_SEQ + 26;
    public static final int DAYMENU27_ID = START_DAYMENU_SEQ + 27;
    public static final int DAYMENU28_ID = START_DAYMENU_SEQ + 28;
    public static final int DAYMENU29_ID = START_DAYMENU_SEQ + 29;
    public static final int DAYMENU30_ID = START_DAYMENU_SEQ + 30;
    public static final int DAYMENU31_ID = START_DAYMENU_SEQ + 31;
    public static final int DAYMENU32_ID = START_DAYMENU_SEQ + 32;
    public static final int DAYMENU33_ID = START_DAYMENU_SEQ + 33;
    public static final int DAYMENU34_ID = START_DAYMENU_SEQ + 34;
    public static final int DAYMENU35_ID = START_DAYMENU_SEQ + 35;
    public static final int DAYMENU36_ID = START_DAYMENU_SEQ + 36;
    public static final int DAYMENU37_ID = START_DAYMENU_SEQ + 37;
    public static final int DAYMENU38_ID = START_DAYMENU_SEQ + 38;
    public static final int DAYMENU39_ID = START_DAYMENU_SEQ + 39;

    public static final DayMenu DAYMENU1 = new DayMenu(DAYMENU1_ID, LocalDate.of(2019, 7, 1), RES1, DISH1, 101);
    public static final DayMenu DAYMENU2 = new DayMenu(DAYMENU2_ID, LocalDate.of(2019, 7, 1), RES1, DISH2, 211);
    public static final DayMenu DAYMENU3 = new DayMenu(DAYMENU3_ID, LocalDate.of(2019, 7, 1), RES1, DISH3, 251);
    public static final DayMenu DAYMENU4 = new DayMenu(DAYMENU4_ID, LocalDate.of(2019, 7, 1), RES2, DISH4, 321);
    public static final DayMenu DAYMENU5 = new DayMenu(DAYMENU5_ID, LocalDate.of(2019, 7, 1), RES3, DISH7, 351);
    public static final DayMenu DAYMENU6 = new DayMenu(DAYMENU6_ID, LocalDate.of(2019, 7, 1), RES3, DISH8, 321);
    public static final DayMenu DAYMENU7 = new DayMenu(DAYMENU7_ID, LocalDate.of(2019, 7, 1), RES4, DISH12, 91);
    public static final DayMenu DAYMENU8 = new DayMenu(DAYMENU8_ID, LocalDate.of(2019, 7, 1), RES5, DISH14, 91);
    public static final DayMenu DAYMENU9 = new DayMenu(DAYMENU9_ID, LocalDate.of(2019, 7, 1), RES7, DISH19, 101);
    public static final DayMenu DAYMENU10 = new DayMenu(DAYMENU10_ID, LocalDate.of(2019, 7, 1), RES7, DISH20, 271);
    public static final DayMenu DAYMENU11 = new DayMenu(DAYMENU11_ID, LocalDate.of(2019, 7, 1), RES7, DISH21, 341);
    public static final DayMenu DAYMENU12 = new DayMenu(DAYMENU12_ID, LocalDate.of(2019, 7, 1), RES9, DISH25, 121);
    public static final DayMenu DAYMENU13 = new DayMenu(DAYMENU13_ID, LocalDate.of(2019, 7, 1), RES9, DISH26, 141);
    public static final DayMenu DAYMENU14 = new DayMenu(DAYMENU14_ID, LocalDate.of(2019, 7, 2), RES1, DISH1, 102);
    public static final DayMenu DAYMENU15 = new DayMenu(DAYMENU15_ID, LocalDate.of(2019, 7, 2), RES1, DISH2, 212);
    public static final DayMenu DAYMENU16 = new DayMenu(DAYMENU16_ID, LocalDate.of(2019, 7, 2), RES2, DISH5, 232);
    public static final DayMenu DAYMENU17 = new DayMenu(DAYMENU17_ID, LocalDate.of(2019, 7, 2), RES2, DISH4, 322);
    public static final DayMenu DAYMENU18 = new DayMenu(DAYMENU18_ID, LocalDate.of(2019, 7, 2), RES3, DISH9, 302);
    public static final DayMenu DAYMENU19 = new DayMenu(DAYMENU19_ID, LocalDate.of(2019, 7, 2), RES3, DISH8, 322);
    public static final DayMenu DAYMENU20 = new DayMenu(DAYMENU20_ID, LocalDate.of(2019, 7, 2), RES4, DISH12, 92);
    public static final DayMenu DAYMENU21 = new DayMenu(DAYMENU21_ID, LocalDate.of(2019, 7, 2), RES5, DISH14, 92);
    public static final DayMenu DAYMENU22 = new DayMenu(DAYMENU22_ID, LocalDate.of(2019, 7, 2), RES5, DISH15, 352);
    public static final DayMenu DAYMENU23 = new DayMenu(DAYMENU23_ID, LocalDate.of(2019, 7, 2), RES7, DISH20, 272);
    public static final DayMenu DAYMENU24 = new DayMenu(DAYMENU24_ID, LocalDate.of(2019, 7, 2), RES8, DISH23, 492);
    public static final DayMenu DAYMENU25 = new DayMenu(DAYMENU25_ID, LocalDate.of(2019, 7, 2), RES9, DISH25, 122);
    public static final DayMenu DAYMENU26 = new DayMenu(DAYMENU26_ID, LocalDate.of(2019, 7, 2), RES8, DISH22, 392);
    public static final DayMenu DAYMENU27 = new DayMenu(DAYMENU27_ID, LocalDate.of(2019, 7, 3), RES1, DISH1, 103);
    public static final DayMenu DAYMENU28 = new DayMenu(DAYMENU28_ID, LocalDate.of(2019, 7, 3), RES2, DISH6, 353);
    public static final DayMenu DAYMENU29 = new DayMenu(DAYMENU29_ID, LocalDate.of(2019, 7, 3), RES1, DISH3, 253);
    public static final DayMenu DAYMENU30 = new DayMenu(DAYMENU30_ID, LocalDate.of(2019, 7, 3), RES2, DISH4, 323);
    public static final DayMenu DAYMENU31 = new DayMenu(DAYMENU31_ID, LocalDate.of(2019, 7, 3), RES5, DISH13, 123);
    public static final DayMenu DAYMENU32 = new DayMenu(DAYMENU32_ID, LocalDate.of(2019, 7, 3), RES3, DISH8, 323);
    public static final DayMenu DAYMENU33 = new DayMenu(DAYMENU33_ID, LocalDate.of(2019, 7, 3), RES4, DISH12, 93);
    public static final DayMenu DAYMENU34 = new DayMenu(DAYMENU34_ID, LocalDate.of(2019, 7, 3), RES8, DISH22, 393);
    public static final DayMenu DAYMENU35 = new DayMenu(DAYMENU35_ID, LocalDate.of(2019, 7, 3), RES8, DISH23, 493);
    public static final DayMenu DAYMENU36 = new DayMenu(DAYMENU36_ID, LocalDate.of(2019, 7, 3), RES8, DISH24, 423);
    public static final DayMenu DAYMENU37 = new DayMenu(DAYMENU37_ID, LocalDate.of(2019, 7, 3), RES7, DISH21, 343);
    public static final DayMenu DAYMENU38 = new DayMenu(DAYMENU38_ID, LocalDate.of(2019, 7, 3), RES9, DISH27, 373);
    public static final DayMenu DAYMENU39 = new DayMenu(DAYMENU39_ID, LocalDate.of(2019, 7, 3), RES9, DISH26, 143);

    public static final List<DayMenu> RES8_DAYMENUS = List.of(DAYMENU24, DAYMENU26, DAYMENU34, DAYMENU35, DAYMENU36);

    public static final List<DayMenu> DAYMENUS_FOR_20190703 = List.of(DAYMENU27, DAYMENU28, DAYMENU29, DAYMENU30,
            DAYMENU31, DAYMENU32, DAYMENU33, DAYMENU34, DAYMENU35, DAYMENU36, DAYMENU37, DAYMENU38, DAYMENU39);

    public static final List<DayMenu> DAYMENUS_FOR_20190702 = List.of(DAYMENU14, DAYMENU15, DAYMENU16, DAYMENU17,
            DAYMENU18, DAYMENU19, DAYMENU20, DAYMENU21, DAYMENU22, DAYMENU23, DAYMENU24, DAYMENU25, DAYMENU26);

    public static final List<DayMenu> DAY_MENUS = List.of(DAYMENU1, DAYMENU2, DAYMENU3, DAYMENU4, DAYMENU5, DAYMENU6,
            DAYMENU7, DAYMENU8, DAYMENU9, DAYMENU10, DAYMENU11, DAYMENU12, DAYMENU13, DAYMENU14, DAYMENU15, DAYMENU16,
            DAYMENU17, DAYMENU18, DAYMENU19, DAYMENU20, DAYMENU21, DAYMENU22, DAYMENU23, DAYMENU24, DAYMENU25, DAYMENU26,
            DAYMENU27, DAYMENU28, DAYMENU29, DAYMENU30, DAYMENU31, DAYMENU32, DAYMENU33, DAYMENU34, DAYMENU35, DAYMENU36,
            DAYMENU37, DAYMENU38, DAYMENU39);

    public static final int START_VOTE_SEQ = 50000;
    public static final int VOTE1_ID = START_VOTE_SEQ + 1;
    public static final int VOTE2_ID = START_VOTE_SEQ + 2;
    public static final int VOTE3_ID = START_VOTE_SEQ + 3;
    public static final int VOTE4_ID = START_VOTE_SEQ + 4;
    public static final int VOTE5_ID = START_VOTE_SEQ + 5;
    public static final int VOTE6_ID = START_VOTE_SEQ + 6;
    public static final int VOTE7_ID = START_VOTE_SEQ + 7;
    public static final int VOTE8_ID = START_VOTE_SEQ + 8;
    public static final int VOTE9_ID = START_VOTE_SEQ + 9;
    public static final int VOTE10_ID = START_VOTE_SEQ + 10;
    public static final int VOTE11_ID = START_VOTE_SEQ + 11;
    public static final int VOTE12_ID = START_VOTE_SEQ + 12;
    public static final int VOTE13_ID = START_VOTE_SEQ + 13;
    public static final int VOTE14_ID = START_VOTE_SEQ + 14;
    public static final int VOTE15_ID = START_VOTE_SEQ + 15;
    public static final int VOTE16_ID = START_VOTE_SEQ + 16;
    public static final int VOTE17_ID = START_VOTE_SEQ + 17;
    public static final int VOTE18_ID = START_VOTE_SEQ + 18;
    public static final int VOTE19_ID = START_VOTE_SEQ + 19;
    public static final int VOTE20_ID = START_VOTE_SEQ + 20;
    public static final int VOTE21_ID = START_VOTE_SEQ + 21;
    public static final int VOTE22_ID = START_VOTE_SEQ + 22;
    public static final int VOTE23_ID = START_VOTE_SEQ + 23;
    public static final int VOTE24_ID = START_VOTE_SEQ + 24;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, LocalDate.of(2019, 7, 1), 18, 109);
    public static final Vote VOTE2 = new Vote(VOTE2_ID, LocalDate.of(2019, 7, 1), 11, 101);
    public static final Vote VOTE3 = new Vote(VOTE3_ID, LocalDate.of(2019, 7, 1), 12, 103);
    public static final Vote VOTE4 = new Vote(VOTE4_ID, LocalDate.of(2019, 7, 1), 13, 105);
    public static final Vote VOTE5 = new Vote(VOTE5_ID, LocalDate.of(2019, 7, 1), 14, 103);
    public static final Vote VOTE6 = new Vote(VOTE6_ID, LocalDate.of(2019, 7, 1), 15, 103);
    public static final Vote VOTE7 = new Vote(VOTE7_ID, LocalDate.of(2019, 7, 1), 16, 101);
    public static final Vote VOTE8 = new Vote(VOTE8_ID, LocalDate.of(2019, 7, 1), 17, 108);
    public static final Vote VOTE9 = new Vote(VOTE9_ID, LocalDate.of(2019, 7, 2), 18, 109);
    public static final Vote VOTE10 = new Vote(VOTE10_ID, LocalDate.of(2019, 7, 2), 11, 107);
    public static final Vote VOTE11 = new Vote(VOTE11_ID, LocalDate.of(2019, 7, 2), 12, 103);
    public static final Vote VOTE12 = new Vote(VOTE12_ID, LocalDate.of(2019, 7, 2), 13, 106);
    public static final Vote VOTE13 = new Vote(VOTE13_ID, LocalDate.of(2019, 7, 2), 14, 103);
    public static final Vote VOTE14 = new Vote(VOTE14_ID, LocalDate.of(2019, 7, 2), 15, 105);
    public static final Vote VOTE15 = new Vote(VOTE15_ID, LocalDate.of(2019, 7, 2), 16, 101);
    public static final Vote VOTE16 = new Vote(VOTE16_ID, LocalDate.of(2019, 7, 2), 17, 102);
    public static final Vote VOTE17 = new Vote(VOTE17_ID, LocalDate.of(2019, 7, 3), 18, 103);
    public static final Vote VOTE18 = new Vote(VOTE18_ID, LocalDate.of(2019, 7, 3), 11, 103);
    public static final Vote VOTE19 = new Vote(VOTE19_ID, LocalDate.of(2019, 7, 3), 12, 103);
    public static final Vote VOTE20 = new Vote(VOTE20_ID, LocalDate.of(2019, 7, 3), 13, 108);
    public static final Vote VOTE21 = new Vote(VOTE21_ID, LocalDate.of(2019, 7, 3), 14, 108);
    public static final Vote VOTE22 = new Vote(VOTE22_ID, LocalDate.of(2019, 7, 3), 15, 108);
    public static final Vote VOTE23 = new Vote(VOTE23_ID, LocalDate.of(2019, 7, 3), 16, 101);
    public static final Vote VOTE24 = new Vote(VOTE24_ID, LocalDate.of(2019, 7, 3), 17, 104);

    public static final List<Vote> VOTES_FOR_20190713 = List.of(VOTE17, VOTE18, VOTE19, VOTE20, VOTE21, VOTE22, VOTE23, VOTE24);

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
