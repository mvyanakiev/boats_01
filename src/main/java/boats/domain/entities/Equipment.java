package boats.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "equipments")
public class Equipment extends BaseEntity {

    private String item;
    private String serialNumber;
    private Boat boat;
    private LocalDate lastCheckedDate;

    public Equipment() {
    }

    @Column(name = "item", nullable = false)
    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "serial_number")
    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @ManyToOne(targetEntity = Boat.class)
//    @JoinColumn(
//            name = "boat_id",
//            referencedColumnName = "id"
//    )
    public Boat getBoat() {
        return this.boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    @Column(name = "last_checked_date")
    public LocalDate getLastCheckedDate() {
        return this.lastCheckedDate;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}
