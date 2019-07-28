package ru.restaurant.voting.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class VoteKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate votingDate;
    private Integer userId;
    private Integer restaurantId;

    public VoteKey() {
    }

    public VoteKey(LocalDate votingDate, Integer userId, Integer restaurantId) {
        this.votingDate = votingDate;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
        if (o == null || getClass() != o.getClass()) return false;
        VoteKey voteKey = (VoteKey) o;
        return votingDate.equals(voteKey.votingDate) &&
                userId.equals(voteKey.userId) &&
                restaurantId.equals(voteKey.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(votingDate, userId, restaurantId);
    }
}
