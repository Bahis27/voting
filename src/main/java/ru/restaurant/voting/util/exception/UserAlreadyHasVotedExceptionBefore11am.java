package ru.restaurant.voting.util.exception;

public class UserAlreadyHasVotedExceptionBefore11am extends RuntimeException {
    public UserAlreadyHasVotedExceptionBefore11am(String message) {
        super(message);
    }
}
