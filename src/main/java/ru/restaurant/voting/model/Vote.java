package ru.restaurant.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes"
        , uniqueConstraints = {@UniqueConstraint(
        columnNames = {"vote_day", "user_id"},
        name = "votes_unique_voteday_userid_idx")
})
public class Vote extends AbstractBaseEntity {

    @Column(name = "vote_day", nullable = false)
    @NotNull
    private LocalDate votingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Integer id, @NotNull LocalDate votingDate, User user, Restaurant restaurant) {
        super(id);
        this.votingDate = votingDate;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getVotingDate(), vote.getUser(), vote.getRestaurant());
    }

    public LocalDate getVotingDate() {
        return votingDate;
    }

    public void setVotingDate(LocalDate votingDate) {
        this.votingDate = votingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}