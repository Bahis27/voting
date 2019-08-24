package ru.restaurant.voting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        if (!super.equals(o)) return false;
        Vote vote = (Vote) o;
        return getVotingDate().equals(vote.getVotingDate()) &&
                getUserId().equals(vote.getUserId()) &&
                getRestaurantId().equals(vote.getRestaurantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getVotingDate(), getUserId(), getRestaurantId());
    }
}