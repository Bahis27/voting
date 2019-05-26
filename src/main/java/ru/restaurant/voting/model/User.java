package ru.restaurant.voting.model;

import java.util.Date;
import java.util.Set;

public class User {
    Integer id;
    String name;
    String email;
    String passwword;
    boolean enabled;
    Date registered;
    Set<Role> roles;
}
