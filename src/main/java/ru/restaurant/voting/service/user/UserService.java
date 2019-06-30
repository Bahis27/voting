package ru.restaurant.voting.service.user;

import ru.restaurant.voting.model.User;
import ru.restaurant.voting.to.UserTo;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    void update(UserTo user);

    List<User> getAll();
}