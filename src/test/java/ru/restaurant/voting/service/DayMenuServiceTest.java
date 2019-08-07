package ru.restaurant.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.service.daymenu.DayMenuService;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DayMenuServiceTest extends AbstractServiceTest {

    @Autowired
    private DayMenuService dayMenuService;

    @Test
    void delete() throws Exception {
        DayMenu deleted = dayMenuService.get(10010);
        dayMenuService.delete(10010);
        List<DayMenu> dayMenus = dayMenuService.getAllForDay(107, LocalDate.of(2019, 7, 1));
        assertFalse(dayMenus.contains(deleted));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dayMenuService.delete(1));
    }
}
