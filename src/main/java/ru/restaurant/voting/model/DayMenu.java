package ru.restaurant.voting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "day_menus"
//        , uniqueConstraints = {@UniqueConstraint(
//                columnNames = {"menu_day", "restaurant_id", "dish_id"},
//                name = "daymenus_unique_menuday_restaurantid_dishid_idx")
//        }
)
public class DayMenu extends AbstractBaseEntity {

    @Column(name = "menu_day", nullable = false, columnDefinition = "date default current_date")
    @NotNull
    private LocalDate menuDate;

    public DayMenu() {
    }

    public DayMenu(Integer id, @NotNull LocalDate menuDate) {
        super(id);
        this.menuDate = menuDate;
    }

    public DayMenu(Integer id) {
        this(id, LocalDate.now());
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    //    private Restaurant restaurant;
    //    private Dish dish;
}
