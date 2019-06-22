package ru.restaurant.voting.web;

public class SecurityUtil {

    private static int authUserId = 0;

    public static void setAuthUserId(int authUserId) {
        SecurityUtil.authUserId = authUserId;
    }

    public static int getAuthUserId() {
        return SecurityUtil.authUserId;
    }

}