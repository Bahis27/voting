package ru.restaurant.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "day_menus"
        , uniqueConstraints = {@UniqueConstraint(
                columnNames = {"menu_day", "restaurant_id", "dish_id"},
                name = "daymenus_unique_menuday_restaurantid_dishid_idx")
        }
)
public class DayMenu extends AbstractBaseEntity {

    @Column(name = "menu_day", nullable = false, columnDefinition = "date default current_date")
    private LocalDate menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dish dish;

    public DayMenu() {
    }

    public DayMenu(DayMenu menu) {
        this(menu.getId(), menu.getMenuDate(), menu.getRestaurant(), menu.getDish());
    }

    public DayMenu(Integer id, LocalDate menuDate, Restaurant restaurant, Dish dish) {
        super(id);
        setMenuDate(menuDate);
        this.restaurant = restaurant;
        this.dish = dish;
    }

    public DayMenu(Integer id, LocalDate menuDate) {
        super(id);
        setMenuDate(menuDate);
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        if (menuDate == null) {
            menuDate = LocalDate.now();
        }
        this.menuDate = menuDate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
