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
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DayMenuRepository extends JpaRepository<DayMenu, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM DayMenu m WHERE m.id=:id")
    int deleteWithId(@Param("id") int id);

    @Transactional
    default boolean delete(int id) {
        return deleteWithId(id) != 0;
    }

    @Transactional
    @Modifying
    @Query("DELETE FROM DayMenu m WHERE m.restaurant.id=:id AND m.menuDate=:date")
    int deleteAllWithRestaurantIdAndMenuDate(@Param("id") int restaurantId, @Param("date") LocalDate date);

    @Transactional
    default boolean deleteAll(int restaurantId, LocalDate date) {
        return deleteAllWithRestaurantIdAndMenuDate(restaurantId, date) != 0;
    }

    @Transactional
    @Override
    DayMenu save(DayMenu dayMenu);

    @Override
    Optional<DayMenu> findById(Integer id);

    @Transactional
    @Query("SELECT DISTINCT m FROM DayMenu m JOIN FETCH m.dish WHERE m.restaurant.id=:id AND m.menuDate=:date")
    List<DayMenu> findAllForDateAndRestaurantId(@Param("id") int restaurantId, @Param("date") LocalDate date);
}