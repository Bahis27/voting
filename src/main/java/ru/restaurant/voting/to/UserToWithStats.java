package ru.restaurant.voting.to;

import org.hibernate.validator.constraints.SafeHtml;
import ru.restaurant.voting.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class UserToWithStats extends NamedTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Email
    @NotBlank
    @Size(max = 100)
    @SafeHtml // https://stackoverflow.com/questions/17480809
    private String email;

    private int stat;

    public UserToWithStats() {
    }

    public UserToWithStats(Integer id,
                           @NotBlank @Size(min = 2, max = 100) @SafeHtml String name,
                           @Email @NotBlank @Size(max = 100) @SafeHtml String email,
                           int stat) {
        super(id, name);
        this.email = email;
        this.stat = stat;
    }

    public UserToWithStats(User user) {
        this(user.getId(), user.getName(), user.getEmail(), 0);
    }

    public UserToWithStats(UserTo userTo) {
        this(userTo.getId(), userTo.getName(), userTo.getEmail(), 0);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserToWithStats)) return false;
        UserToWithStats that = (UserToWithStats) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName()) &&
                getStat() == that.getStat() &&
                getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getStat());
    }
}
