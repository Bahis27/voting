package ru.restaurant.voting.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

//    private List<DayMenu> dayMenus;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
