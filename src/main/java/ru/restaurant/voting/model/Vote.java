package ru.restaurant.voting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes"
        , uniqueConstraints = {@UniqueConstraint(
        columnNames = {"vote_day", "user_id", "restaurant_id"},
        name = "votes_unique_voteday_userid_restaurantid_idx")
})
public class Vote extends AbstractBaseEntity {

    @Column(name = "vote_day", nullable = false)
    @NotNull
    private LocalDate votingDate;

    @Column(name = "user_id", nullable = false)
    @NotNull
    private Integer userId;

    @Column(name = "restaurant_id", nullable = false)
    @NotNull
    private Integer restaurantId;

    public Vote() {
    }

    public Vote(Integer id, @NotNull LocalDate votingDate, @NotNull Integer userId, @NotNull Integer restaurantId) {
        super(id);
        this.votingDate = votingDate;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getVotingDate(), vote.getUserId(), vote.getRestaurantId());
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