package ru.restaurant.voting.service.daymenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.restaurant.voting.model.DayMenu;
import ru.restaurant.voting.repository.DayMenuRepository;
import ru.restaurant.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DayMenuServiceImpl implements DayMenuService {


    private final DayMenuRepository dayMenuRepository;

    @Autowired
    public DayMenuServiceImpl(DayMenuRepository dayMenuRepository) {
        this.dayMenuRepository = dayMenuRepository;
    }

    @Override
    public DayMenu create(DayMenu dayMenu) {
        Assert.notNull(dayMenu, "dayMenu must not be null");
        return dayMenuRepository.save(dayMenu);
    }

    @Override
    public void update(DayMenu dayMenu) {
        Assert.notNull(dayMenu, "dayMenu must not be null");
        checkNotFoundWithId(dayMenu, dayMenu.getId());
        dayMenuRepository.save(dayMenu);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(dayMenuRepository.delete(id), id);
    }

    @Override
    public void deleteAll(int restaurantId, LocalDate date) throws NotFoundException {
        checkNotFoundWithId(dayMenuRepository.deleteAll(restaurantId, date), restaurantId);
    }

    @Override
    public DayMenu get(int id) throws NotFoundException {
        return checkNotFoundWithId(dayMenuRepository.findById(id).orElse(null), id);
    }

    @Override
    public List<DayMenu> getAllForDay(int restaurantId, LocalDate date) throws NotFoundException {
        return dayMenuRepository.findAllForDateAndRestaurantId(restaurantId, date);
    }
}
