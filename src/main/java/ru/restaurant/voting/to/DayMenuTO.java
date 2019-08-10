package ru.restaurant.voting.to;

import ru.restaurant.voting.model.DayMenu;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class DayMenuTO extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "menu_day", nullable = false, columnDefinition = "date default current_date")
    @NotNull
    private LocalDate menuDate;

    public DayMenuTO() {
    }

    public DayMenuTO(DayMenu dayMenu) {
        this(dayMenu.getId(), dayMenu.getMenuDate());
    }

    public DayMenuTO(Integer id, @NotNull LocalDate menuDate) {
        super(id);
        this.menuDate = menuDate;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayMenuTO)) return false;
        DayMenuTO dayMenuTO = (DayMenuTO) o;
        return getId().equals(dayMenuTO.getId()) &&
                getMenuDate().equals(dayMenuTO.getMenuDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMenuDate());
    }
}
