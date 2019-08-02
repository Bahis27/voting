package ru.restaurant.voting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "votes"
        , uniqueConstraints = {@UniqueConstraint(
        columnNames = {"vote_day", "user_id", "restaurant_id"},
        name = "votes_unique_voteday_userid_restaurantid_idx")
}
)
@IdClass(VoteKey.class)
public class Vote {
    @Id
    @Column(name = "vote_day", nullable = false)
    @NotNull
    private LocalDate votingDate;

    @Id
    @Column(name = "user_id", nullable = false)
    @NotNull
    private Integer userId;

    @Id
    @Column(name = "restaurant_id", nullable = false)
    @NotNull
    private Integer restaurantId;

    public Vote() {
    }

    public Vote(@NotNull LocalDate votingDate, @NotNull Integer userId, @NotNull Integer restaurantId) {
        this.votingDate = votingDate;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public Vote(VoteKey voteKey) {
        this(voteKey.getVotingDate(), voteKey.getUserId(), voteKey.getRestaurantId());
    }

    public LocalDate getVotingDate() {
        return votingDate;
    }

    public void setVotingDate(LocalDate votingDate) {
        this.votingDate = votingDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "votingDate=" + votingDate +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return getVotingDate().equals(vote.getVotingDate()) &&
                getUserId().equals(vote.getUserId()) &&
                getRestaurantId().equals(vote.getRestaurantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVotingDate(), getUserId(), getRestaurantId());
    }
}