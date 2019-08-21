package ru.restaurant.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dish dish;

    @Column(name = "price", nullable = false)
    @Range(min = 1)
    @NotNull
    private Integer price;

    public DayMenu() {
    }

    public DayMenu(DayMenu menu) {
        this(menu.getId(), menu.getMenuDate(), menu.getRestaurant(), menu.getDish(), menu.getPrice());
    }

    public DayMenu(Integer id, LocalDate menuDate, Restaurant restaurant, Dish dish, @Range(min = 1) @NotNull Integer price) {
        super(id);
        setMenuDate(menuDate);
        this.restaurant = restaurant;
        this.dish = dish;
        this.price = price;
    }

    public DayMenu(Integer id, LocalDate menuDate, @Range(min = 1) @NotNull Integer price) {
        super(id);
        setMenuDate(menuDate);
        this.price = price;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
