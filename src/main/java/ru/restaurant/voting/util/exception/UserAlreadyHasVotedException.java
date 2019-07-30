package ru.restaurant.voting.util.exception;

public class UserAlreadyHasVotedException extends RuntimeException {
    public UserAlreadyHasVotedException(String message) {
        super(message);
    }
}
