package ru.restaurant.voting.to;

import org.hibernate.validator.constraints.SafeHtml;
import ru.restaurant.voting.model.Restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class RestaurantToWithStats extends RestaurantNamesTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int stat;

    public RestaurantToWithStats() {
    }

    public RestaurantToWithStats(RestaurantNamesTo restaurantNamesTo) {
        this(restaurantNamesTo.getId(), restaurantNamesTo.getName(), 0);
    }

    public RestaurantToWithStats(Integer id, @NotBlank @Size(min = 2, max = 100) @SafeHtml String name, int stat) {
        super(id, name);
        this.stat = stat;
    }

    public RestaurantToWithStats(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), 0);
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantNamesTo)) return false;
        RestaurantNamesTo that = (RestaurantNamesTo) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
