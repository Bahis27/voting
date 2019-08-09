package ru.restaurant.voting.service.daymenu;

import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface DayMenuService {

    DayMenu create(DayMenu dayMenu, int restaurantId);

    void update(DayMenu dayMenu, int restaurantId);

    List<DayMenu> getAll(int restaurantId);

    List<DayMenu> getAllForDay(int restaurantId, LocalDate date);

    DayMenu get(int id, int restaurantId) throws NotFoundException;

    void delete(int id, int restaurantId) throws NotFoundException;

    void deleteAllForDay(int restaurantId, LocalDate date);
}
