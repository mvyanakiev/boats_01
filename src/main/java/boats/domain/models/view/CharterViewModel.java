package boats.domain.models.view;

import boats.domain.entities.Boat;
import boats.domain.entities.Direction;
import boats.domain.entities.People;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;



public class CharterViewModel {

    private String id;
    private Boat boat;
    private LocalDate startDate;
    private BigDecimal price;
    private People customer;
    private Direction direction;

    public CharterViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boat getBoat() {
        return this.boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public Date getStartDate() {
        Date date = Date.from(this.startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public People getCustomer() {
        return this.customer;
    }

    public void setCustomer(People customer) {
        this.customer = customer;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
