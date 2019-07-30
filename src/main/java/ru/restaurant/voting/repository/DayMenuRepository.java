package ru.restaurant.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.model.Dish;
import ru.restaurant.voting.model.Restaurant;

import java.time.LocalDate;

@Repository
@Transactional(readOnly = true)
public interface DayMenuRepository extends JpaRepository<DayMenu, Integer> {

    @Transactional
    @Override
    void deleteById(Integer id);

    @Transactional
    @Override
    DayMenu save(DayMenu dayMenu);

    DayMenu findByMenuDateAndRestaurantAndDish(LocalDate day, Restaurant restaurant, Dish dish);

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM DayMenu m WHERE m.restaurant.id=:restaurantId AND m.menuDate=:day")
//    void deleteForDayForRestaurant(@Param("restaurantId") int restaurantId, @Param("day") LocalDate day);
}