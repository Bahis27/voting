package ru.restaurant.voting.to;

import org.hibernate.validator.constraints.SafeHtml;
import ru.restaurant.voting.model.Restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class RestaurantNamesTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    private String name;

    public RestaurantNamesTo() {
    }

    public RestaurantNamesTo(Integer id, @NotBlank @Size(min = 2, max = 100) @SafeHtml String name) {
        super(id);
        this.name = name;
    }

    public RestaurantNamesTo(Restaurant restaurant) {
        super(restaurant.getId());
        this.name = restaurant.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
