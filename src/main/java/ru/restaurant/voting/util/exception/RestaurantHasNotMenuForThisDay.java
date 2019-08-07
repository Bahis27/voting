package ru.restaurant.voting.util.exception;

public class RestaurantHasNotMenuForThisDay extends RuntimeException {
    public RestaurantHasNotMenuForThisDay(String message) {
        super(message);
    }
}
