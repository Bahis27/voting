package ru.restaurant.voting.model;

import ru.restaurant.voting.to.RestaurantNamesTo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants"
        , uniqueConstraints = {@UniqueConstraint(
        columnNames = "name",
        name = "restaurants_unique_name_idx")
})
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<DayMenu> dayMenus;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, List<DayMenu> dayMenus) {
        super(id, name);
        this.dayMenus = dayMenus;
    }

    public Restaurant(RestaurantNamesTo restaurantNamesTo) {
        this(restaurantNamesTo.getId(), restaurantNamesTo.getName());
    }

    public Restaurant(Restaurant restaurant) {
        super(restaurant.getId(), restaurant.getName());
        this.setDayMenus(restaurant.getDayMenus());
    }

    public List<DayMenu> getDayMenus() {
        return dayMenus;
    }

    public void setDayMenus(List<DayMenu> dayMenus) {
        this.dayMenus = dayMenus;
    }
}
