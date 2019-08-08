package ru.restaurant.voting.to;

import org.hibernate.validator.constraints.SafeHtml;
import ru.restaurant.voting.model.Restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class RestaurantTo extends NamedTo implements Serializable {
    private static final long serialVersionUID = 1L;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, @NotBlank @Size(min = 2, max = 100) @SafeHtml String name) {
        super(id, name);
    }

    public RestaurantTo(Restaurant restaurant) {
        super(restaurant.getId(), restaurant.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantTo)) return false;
        RestaurantTo that = (RestaurantTo) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
