package ru.restaurant.voting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes"
//        , uniqueConstraints = {@UniqueConstraint(
//        columnNames = {"vote_day", "user_id"},
//        name = "votes_unique_voteday_userid_idx")
//}
)
public class Vote {
    @Column(name = "vote_day", nullable = false)
    @NotNull
    private LocalDate votingDate;


//    User user;
//    Restaurant restaurant;
}
