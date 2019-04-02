package boats.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "repairs")
public class Repair extends BaseEntity {

    private Boat boat;
    private People supplier;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private String note;
    private RepairType repairType;

    public Repair() {
    }

    @ManyToOne(targetEntity = Boat.class)
//    @JoinColumn(
//            name = "boat_id",
//            referencedColumnName = "id",
//            nullable = false
//    )
    public Boat getBoat() {
        return this.boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    @OneToOne
    @JoinColumn(name = "people_id")
    public People getSupplier() {
        return this.supplier;
    }

    public void setSupplier(People supplier) {
        this.supplier = supplier;
    }

    @Column(name = "start_date", nullable = false)
    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "note")
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "repair_type", nullable = false)
    public RepairType getRepairType() {
        return this.repairType;
    }

    public void setRepairType(RepairType repairType) {
        this.repairType = repairType;
    }
}
