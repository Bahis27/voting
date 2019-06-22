package ru.restaurant.voting.web.user;

import org.springframework.stereotype.Controller;
import ru.restaurant.voting.model.User;

import static ru.restaurant.voting.web.SecurityUtil.getAuthUserId;

@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(getAuthUserId());
    }

    public void delete() {
        super.delete(getAuthUserId());
    }

    public void update(User user) {
        super.update(user, getAuthUserId());
    }
}