package ru.restaurant.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.service.daymenu.DayMenuService;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.voting.TestData.*;

public class DayMenuServiceTest extends AbstractServiceTest {

    @Autowired
    private DayMenuService dayMenuService;

    @Test
    void create() throws Exception {
        DayMenu newDayMenu = new DayMenu(null, LocalDate.now(), RES4, DISH10);
        DayMenu created = dayMenuService.create(newDayMenu, RES4_ID);
        newDayMenu.setId(created.getId());
        assertMatch(newDayMenu, created);
    }

    @Test
    void duplicateCreate() throws Exception {
        DayMenu newDayMenu = new DayMenu(DAYMENU5);
        newDayMenu.setId(null);
        assertThrows(DataAccessException.class, () ->
                dayMenuService.create(newDayMenu, RES3_ID));
    }

    @Test
    void update() throws Exception {
        DayMenu updated = new DayMenu(DAYMENU7);
        updated.setMenuDate(LocalDate.now());
        dayMenuService.update(new DayMenu(updated), RES4_ID);
        assertMatch(updated, dayMenuService.get(DAYMENU7_ID, RES4_ID));
    }

    @Test
    void delete() throws Exception {
        dayMenuService.delete(DAYMENU10_ID, RES7_ID);
        List<DayMenu> dayMenus = dayMenuService.getAllForDay(RES7_ID, LocalDate.of(2019, 7, 1));
        assertFalse(dayMenus.contains(DAYMENU10));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dayMenuService.delete(1, RES6_ID));
    }

    @Test
    void get() throws Exception {
        DayMenu dayMenu = dayMenuService.get(DAYMENU12_ID, RES9_ID);
        assertMatch(dayMenu, DAYMENU12);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dayMenuService.get(25, RES2_ID));
    }

    @Test
    void deleteAll() throws Exception {
        dayMenuService.deleteAllForDay(RES2_ID, LocalDate.of(2019, 7, 1));
        List<DayMenu> allForDay = dayMenuService.getAllForDay(RES2_ID, LocalDate.of(2019, 7, 1));
        assertTrue(allForDay.isEmpty());
    }

    @Test
    void getAllForDay() throws Exception {
        List<DayMenu> allForDay = dayMenuService.getAllForDay(RES4_ID, LocalDate.of(2019, 7, 3));
        assertMatch(allForDay, List.of(DAYMENU33));
    }

    @Test
    void getAll() throws Exception {
        List<DayMenu> allDayMenus = dayMenuService.getAll(RES5_ID);
        assertMatch(allDayMenus, List.of(DAYMENU8, DAYMENU21, DAYMENU22, DAYMENU31));
    }
}
