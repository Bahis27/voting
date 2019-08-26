package ru.restaurant.voting.to;

import ru.restaurant.voting.model.Vote;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class VoteTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private LocalDate menuDate;

    @NotBlank
    @Size(min = 2, max = 100)
    private String restaurantName;

    @Email
    @NotBlank
    @Size(min = 2, max = 100)
    private String userEmail;

    public VoteTo() {
    }

    public VoteTo(Integer id,
                  @NotNull LocalDate menuDate,
                  @NotBlank @Size(min = 2, max = 100) String restaurantName,
                  @Email @NotBlank @Size(min = 2, max = 100) String userEmail) {
        super(id);
        this.menuDate = menuDate;
        this.restaurantName = restaurantName;
        this.userEmail = userEmail;
    }

    public VoteTo(Vote vote) {
        this(vote.getId(), vote.getVotingDate(), vote.getRestaurant().getName(), vote.getUser().getEmail());
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteTo)) return false;
        VoteTo voteTo = (VoteTo) o;
        return getId().equals(voteTo.getId()) &&
                getMenuDate().equals(voteTo.getMenuDate()) &&
                getRestaurantName().equals(voteTo.getRestaurantName()) &&
                getUserEmail().equals(voteTo.getUserEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMenuDate(), getRestaurantName(), getUserEmail());
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", menuDate=" + menuDate +
                ", restaurantName='" + restaurantName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
