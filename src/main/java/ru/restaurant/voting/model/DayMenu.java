package ru.restaurant.voting.model;

import java.time.LocalDate;
import java.util.List;

public class DayMenu {
    Integer id;
    LocalDate dateMenu;
    Integer menuPrice;
    Restaurant restaurant;
    List<Dish> dishes;
}
