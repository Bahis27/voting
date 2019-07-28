package ru.restaurant.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static java.time.LocalDate.now;

@Entity
@Table(name = "day_menus"
        , uniqueConstraints = {@UniqueConstraint(
                columnNames = {"menu_day", "restaurant_id", "dish_id"},
                name = "daymenus_unique_menuday_restaurantid_dishid_idx")
        }
)
public class DayMenu extends AbstractBaseEntity {

    @Column(name = "menu_day", nullable = false, columnDefinition = "date default current_date")
    @NotNull
    private LocalDate menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "dish_id")
    private Dish dish;

    public DayMenu() {
    }

    public DayMenu(@NotNull LocalDate menuDate, Restaurant restaurant, Dish dish) {
        this.menuDate = menuDate;
        this.restaurant = restaurant;
        this.dish = dish;
    }

    public DayMenu(Integer id, @NotNull LocalDate menuDate, Restaurant restaurant, Dish dish) {
        super(id);
        this.menuDate = menuDate;
        this.restaurant = restaurant;
        this.dish = dish;
    }

    public DayMenu(Restaurant restaurant, Dish dish) {
        this.menuDate = now();
        this.restaurant = restaurant;
        this.dish = dish;
    }

    public DayMenu(Integer id, Restaurant restaurant, Dish dish) {
        super(id);
        this.menuDate = now();
        this.restaurant = restaurant;
        this.dish = dish;
    }

    public DayMenu(@NotNull DayMenu dayMenu) {
        this.setId(dayMenu.getId());
        this.setMenuDate(dayMenu.getMenuDate());
        this.setRestaurant(dayMenu.getRestaurant());
        this.setDish(dayMenu.getDish());
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
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
