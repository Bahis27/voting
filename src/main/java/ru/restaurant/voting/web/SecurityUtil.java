package ru.restaurant.voting.web;

import ru.restaurant.voting.model.AbstractBaseEntity;

public class SecurityUtil {

    private static int id = AbstractBaseEntity.START_SEQ;

    private SecurityUtil() {
    }

    public static int getAuthUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }
}