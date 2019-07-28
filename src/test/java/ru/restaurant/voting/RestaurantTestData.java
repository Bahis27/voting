package ru.restaurant.voting;

import ru.restaurant.voting.model.Restaurant;

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
    public static final Restaurant RES2 = new Restaurant(RES1_ID, "Wok And Roll");
    public static final Restaurant RES3 = new Restaurant(RES1_ID, "Life of Pie");
    public static final Restaurant RES4 = new Restaurant(RES1_ID, "Lord of the Fries");
    public static final Restaurant RES5 = new Restaurant(RES1_ID, "Pita Pan");
    public static final Restaurant RES6 = new Restaurant(RES1_ID, "Why Not Bar");
    public static final Restaurant RES7 = new Restaurant(RES1_ID, "Planet of the Crepes");
    public static final Restaurant RES8 = new Restaurant(RES1_ID, "Thai Tanic");
    public static final Restaurant RES9 = new Restaurant(RES1_ID, "He is Not Here Bar");
}
