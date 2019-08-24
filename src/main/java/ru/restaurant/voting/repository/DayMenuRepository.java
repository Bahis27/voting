package ru.restaurant.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.voting.model.DayMenu;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DayMenuRepository extends JpaRepository<DayMenu, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM DayMenu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DayMenu m WHERE m.restaurant.id=:id AND m.menuDate=:date")
    int deleteAll(@Param("id") int restaurantId, @Param("date") LocalDate date);

    @Transactional
    @Override
    DayMenu save(DayMenu dayMenu);

    @Query("SELECT m FROM DayMenu m")
    List<DayMenu> getAll();

    @Query("SELECT DISTINCT m FROM DayMenu m JOIN FETCH m.dish WHERE m.restaurant.id=:restaurantId AND m.menuDate=:date")
    List<DayMenu> getAllForDateAndRestaurantId(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    @Query("SELECT DISTINCT m FROM DayMenu m JOIN FETCH m.dish WHERE m.menuDate=:date")
    List<DayMenu> getAllForDate(@Param("date") LocalDate date);

    @Query("SELECT DISTINCT m FROM DayMenu m JOIN FETCH m.dish WHERE m.restaurant.id=:restaurantId")
    List<DayMenu> getAllByRestaurantId(@Param("restaurantId") int restaurantId);

    @Query("SELECT DISTINCT m FROM DayMenu m JOIN FETCH m.dish JOIN FETCH m.restaurant WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    DayMenu get(@Param("id") int id, @Param("restaurantId") int restaurantId);
}