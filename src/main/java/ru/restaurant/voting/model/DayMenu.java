package ru.restaurant.voting.model;

import java.time.LocalDate;
import java.util.List;

public class DayMenu {
    Integer id;
    LocalDate dateMenu;
    Restaurant restaurant;
    List<Dish> dishes;
}
