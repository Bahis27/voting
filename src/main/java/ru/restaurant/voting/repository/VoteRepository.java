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

    @Query("SELECT DISTINCT v FROM Vote v JOIN FETCH v.user JOIN FETCH v.restaurant WHERE v.votingDate=:date ORDER BY v.id")
    List<Vote> getAllForDate(@Param("date") LocalDate date);

    @Query("SELECT DISTINCT v FROM Vote v JOIN FETCH v.user JOIN FETCH v.restaurant r JOIN FETCH r.dayMenus m JOIN FETCH m.dish WHERE m.menuDate=v.votingDate AND v.id=:id")
    Vote get(@Param("id") int id);

    Optional<Vote> findByUserIdAndVotingDate(int userId, LocalDate votingDay);

    @Transactional
    @Override
    Vote save(Vote vote);

    @Query("SELECT DISTINCT v FROM Vote v JOIN FETCH v.user u JOIN FETCH v.restaurant WHERE v.votingDate=:date AND u.id=:userId ORDER BY u.email, v.id")
    List<Vote> getAllForDateForUser(@Param("date") LocalDate date, @Param("userId") int userId);

    @Query("SELECT DISTINCT v FROM Vote v JOIN FETCH v.user JOIN FETCH v.restaurant r WHERE v.votingDate=:date AND r.id=:restaurantId ORDER BY r.name, v.id")
    List<Vote> getAllForDateForRestaurant(@Param("date") LocalDate date, @Param("restaurantId") int restaurantId);
}
