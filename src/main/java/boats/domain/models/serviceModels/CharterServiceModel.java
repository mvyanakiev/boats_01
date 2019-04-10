package boats.domain.models.serviceModels;

import boats.domain.entities.Boat;
import boats.domain.entities.Direction;
import boats.domain.entities.People;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CharterServiceModel extends BaseServiceModel {

    private Boat boat;
    private LocalDate startDate;
    private BigDecimal price;
    private People customer;
    private Direction direction;

    public CharterServiceModel() {
    }

    @NotNull
    public Boat getBoat() {
        return this.boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    @NotNull
    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @NotNull
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    public People getCustomer() {
        return this.customer;
    }

    public void setCustomer(People customer) {
        this.customer = customer;
    }

    @NotNull
    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
