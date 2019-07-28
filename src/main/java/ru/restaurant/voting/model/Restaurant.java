package ru.restaurant.voting.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "restaurant",
            orphanRemoval = true,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<DayMenu> dayMenus;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
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
