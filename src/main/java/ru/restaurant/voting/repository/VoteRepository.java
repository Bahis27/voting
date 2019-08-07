package ru.restaurant.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Override
    Vote save(Vote vote);

    Optional<Vote> findByUserIdAndVotingDate(int userId, LocalDate votingDay);

    @Transactional
    void deleteByUserIdAndVotingDate(int userId, LocalDate votingDay);

    @Query("SELECT v FROM Vote v WHERE v.votingDate=:date AND v.restaurantId=:id")
    List<Vote> getAllForDate(@Param("date") LocalDate date, @Param("id") int restaurantId);

    @Query("SELECT v FROM Vote v WHERE v.restaurantId=:id")
    List<Vote> getAll(@Param("id") int restaurantId);
}
