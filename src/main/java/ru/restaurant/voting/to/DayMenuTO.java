package ru.restaurant.voting.to;

import org.hibernate.validator.constraints.Range;
import ru.restaurant.voting.model.DayMenu;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class DayMenuTO extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private LocalDate menuDate;

    @Range(min = 1)
    @NotNull
    private Integer price;

    public DayMenuTO() {
    }

    public DayMenuTO(DayMenu dayMenu) {
        this(dayMenu.getId(), dayMenu.getMenuDate(), dayMenu.getPrice());
    }

    public DayMenuTO(Integer id, @NotNull LocalDate menuDate, @Range(min = 1) @NotNull Integer price) {
        super(id);
        this.menuDate = menuDate;
        this.price = price;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayMenuTO)) return false;
        DayMenuTO dayMenuTO = (DayMenuTO) o;
        return getId().equals(dayMenuTO.getId()) &&
                getMenuDate().equals(dayMenuTO.getMenuDate()) &&
                getPrice().equals(dayMenuTO.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMenuDate(), getPrice());
    }
}
