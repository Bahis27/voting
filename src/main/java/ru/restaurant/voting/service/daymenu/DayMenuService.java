package ru.restaurant.voting.service.daymenu;

import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface DayMenuService {

    DayMenu create(DayMenu dayMenu);

    void update(DayMenu dayMenu);

    void delete(int id) throws NotFoundException;

    void deleteAll(int restaurantId, LocalDate date) throws NotFoundException;

    DayMenu get(int id) throws NotFoundException;

    List<DayMenu> getAllForDay(int restaurantId, LocalDate date) throws NotFoundException;
}
