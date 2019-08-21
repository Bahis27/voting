package ru.restaurant.voting.to;

import org.hibernate.validator.constraints.SafeHtml;
import ru.restaurant.voting.model.Dish;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class DishTo extends NamedTo implements Serializable {
    private static final long serialVersionUID = 1L;

    public DishTo() {
    }

    public DishTo(Dish dish) {
        this(dish.getId(), dish.getName());
    }

    public DishTo(Integer id, @NotBlank @Size(min = 2, max = 100) @SafeHtml String name) {
        super(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DishTo)) return false;
        DishTo dishTo = (DishTo) o;
        return getId().equals(dishTo.getId()) &&
                getName().equals(dishTo.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}