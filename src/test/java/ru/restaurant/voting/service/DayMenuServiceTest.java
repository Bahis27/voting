package ru.restaurant.voting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.service.daymenu.DayMenuService;
import ru.restaurant.voting.to.DayMenuTO;
import ru.restaurant.voting.util.ToUtil;
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
        DayMenu newDayMenu = new DayMenu(null, LocalDate.now(), 555);
        DayMenu created = dayMenuService.create(newDayMenu, RES4_ID, DISH10_ID);
        newDayMenu.setId(created.getId());
        assertMatch(newDayMenu, created);
    }

    @Test
    void duplicateCreate() throws Exception {
        DayMenu newDayMenu = new DayMenu(DAYMENU5);
        newDayMenu.setId(null);
        assertThrows(DataAccessException.class, () ->
                dayMenuService.create(newDayMenu, RES3_ID, DISH7_ID));
    }

    @Test
    void update() throws Exception {
        DayMenu updated = new DayMenu(DAYMENU7);
        updated.setMenuDate(LocalDate.now());
        dayMenuService.update(new DayMenu(updated), RES4_ID, DISH12_ID);
        assertMatch(updated, dayMenuService.get(DAYMENU7_ID, RES4_ID));
    }

    @Test
    void updateWithAlreadyExistingDayMenuInThisDay() throws Exception {
        DayMenu updated = new DayMenu(DAYMENU1);
        assertThrows(DataAccessException.class, () ->
                dayMenuService.update(new DayMenu(updated), RES1_ID, DISH2_ID));
    }

    @Test
    void delete() throws Exception {
        dayMenuService.delete(DAYMENU10_ID, RES7_ID);
        List<DayMenu> dayMenus = dayMenuService.getAllForDayByRestaurantId(RES7_ID, LocalDate.of(2019, 7, 1));
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
        List<DayMenu> allForDay = dayMenuService.getAllForDayByRestaurantId(RES2_ID, LocalDate.of(2019, 7, 1));
        assertTrue(allForDay.isEmpty());
    }

    @Test
    void getAllForDayByRestaurantId() throws Exception {
        List<DayMenu> allForDay = dayMenuService.getAllForDayByRestaurantId(RES4_ID, LocalDate.of(2019, 7, 3));
        assertMatch(allForDay, List.of(DAYMENU33));
    }

    @Test
    void getAllForDay() throws Exception {
        List<DayMenu> allForDay = dayMenuService.getAllForDay(LocalDate.of(2019, 7, 3));
        assertMatch(allForDay, DAYMENUS_FOR_20190703);
    }

    @Test
    void getAllForRestaurant() throws Exception {
        List<DayMenu> allDayMenus = dayMenuService.getAllForRestaurantId(RES5_ID);
        assertMatch(allDayMenus, List.of(DAYMENU8, DAYMENU21, DAYMENU22, DAYMENU31));
    }

    @Test
    void getAll() throws Exception {
        List<DayMenuTO> all = dayMenuService.getAll();
        assertMatch(all, ToUtil.dayMenusAsToList(DAY_MENUS));
    }
}
