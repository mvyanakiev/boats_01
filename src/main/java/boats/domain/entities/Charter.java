package boats.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "charters")
public class Charter extends BaseEntity{

    private Boat boat;
    private LocalDate startDate;
    private BigDecimal price;
    private People customer;
    private Direction direction;

    public Charter() {
    }

    @ManyToOne(targetEntity = Boat.class)
    @JoinColumn(
            name = "boat_id",
            referencedColumnName = "id",
            nullable = false
    )
    public Boat getBoat() {
        return this.boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    @Column(name = "start_date", nullable = false)
    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne(targetEntity = People.class)
    @JoinColumn(
            name = "people_id",
            referencedColumnName = "id",
            nullable = false

    )
    public People getCustomer() {
        return this.customer;
    }

    public void setCustomer(People customer) {
        this.customer = customer;
    }

    @ManyToOne(targetEntity = Direction.class)
    @JoinColumn(
            name = "direction_id",
            referencedColumnName = "id",
            nullable = false
    )
    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
