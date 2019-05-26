package ru.restaurant.voting.model;

import java.time.LocalDate;

public class Vote {
    Integer id;
    LocalDate votingDate;
    User user;
    Restaurant restaurant;
}
