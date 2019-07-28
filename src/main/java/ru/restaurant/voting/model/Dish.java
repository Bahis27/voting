package ru.restaurant.voting.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes"
//        , uniqueConstraints = {@UniqueConstraint(
//                columnNames = {"restaurant_id", "name"},
//                name = "dishes_unique_restaurantid_dishname_idx")
//        }
)
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 1)
    @NotNull
    private Integer price;

    public Dish() {
    }

    public Dish(Integer id, String name, @Range(min = 1) @NotNull Integer price) {
        super(id, name);
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    //    private Restaurant restaurant;
}
