package ru.restaurant.voting.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.restaurant.voting.model.Role;
import ru.restaurant.voting.model.User;
import ru.restaurant.voting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.restaurant.voting.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = userService.create(newUser);
        newUser.setId(created.getId());
        assertMatch(newUser, created);
        assertMatch(userService.getAll(), ADMIN, USER5, USER1, USER4, newUser, USER3, USER7, USER8, USER6, USER2);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        userService.create(new User(null, "Duplicate", "simple@mail.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void delete() throws Exception {
        userService.delete(USER3_ID);
        assertMatch(userService.getAll(), ADMIN, USER5, USER1, USER4, USER7, USER8, USER6, USER2);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        userService.delete(1);
    }

    @Test
    public void get() throws Exception {
        User user = userService.get(USER5_ID);
        assertMatch(user, USER5);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        userService.get(15);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = userService.getByEmail("friendlyspidey@gmail.com");
        assertMatch(user, USER6);
    }

    @Test
    public void update() throws Exception {
        User updated = new User(USER6);
        updated.setName("Venom");
        userService.update(updated);
        assertMatch(userService.get(USER6_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = userService.getAll();
        assertMatch(userService.getAll(), ADMIN, USER5, USER1, USER4, USER3, USER7, USER8, USER6, USER2);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> userService.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
    }
}