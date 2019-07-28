package ru.restaurant.voting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes"
        , uniqueConstraints = {@UniqueConstraint(
        columnNames = {"vote_day", "user_id"},
        name = "votes_unique_voteday_userid_idx")
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
}
