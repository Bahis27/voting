package ru.restaurant.voting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.restaurant.voting.model.Role;
import ru.restaurant.voting.model.User;
import ru.restaurant.voting.service.user.UserService;
import ru.restaurant.voting.util.JpaUtil;
import ru.restaurant.voting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.restaurant.voting.TestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = userService.create(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(newUser, created, "password", "registered");
        assertMatch(userService.getAll(), newUser, ADMIN, USER5, USER1, USER4, USER3, USER7, USER8, USER6, USER2);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                userService.create(new User(null, "Duplicate", "simple@mail.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    void delete() throws Exception {
        userService.delete(USER3_ID);
        assertMatch(userService.getAll(), ADMIN, USER5, USER1, USER4, USER7, USER8, USER6, USER2);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.delete(1));
    }

    @Test
    void get() throws Exception {
        User user = userService.get(ADMIN_ID);
        assertMatch(user, ADMIN, "password", "registered");
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = userService.getByEmail("friendlyspidey@gmail.com");
        assertMatch(user, USER6, "password", "registered");
    }

    @Test
    void update() throws Exception {
        User updated = new User(USER6);
        updated.setName("Venom");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        userService.update(new User(updated));
        assertMatch(userService.get(USER6_ID), updated, "password", "registered");
    }

    @Test
    void getAll() throws Exception {
        List<User> all = userService.getAll();
        assertMatch(all, ADMIN, USER5, USER1, USER4, USER3, USER7, USER8, USER6, USER2);
    }

    @Test
    void testValidation() throws Exception {
        validateRootCause(() -> userService.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@yandex.ru", "", Role.ROLE_USER)), ConstraintViolationException.class);
    }
}