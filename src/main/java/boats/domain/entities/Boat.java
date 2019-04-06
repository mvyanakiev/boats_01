package boats.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boats")
public class Boat extends BaseEntity {

    private String producer;
    private String model;
    private String name;
    private int cabins;
    private int toilets;
    private double draft;
    private int waterTank;
    private int fuelTank;
    private LocalDate lastCheckedDate;
    private BigDecimal price;
//    private List<Equipment> equipment;
//    private List<Repair> repairs;

    public Boat() {
//        this.equipment = new ArrayList<>();
    }

    @Column(name = "producer", nullable = false)
    public String getProducer() {
        return this.producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Column(name = "model", nullable = false)
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "cabins")
    public int getCabins() {
        return this.cabins;
    }

    public void setCabins(int cabins) {
        this.cabins = cabins;
    }

    @Column(name = "toilets")
    public int getToilets() {
        return this.toilets;
    }

    public void setToilets(int toilets) {
        this.toilets = toilets;
    }

    @Column(name = "draft")
    public double getDraft() {
        return this.draft;
    }

    public void setDraft(double draft) {
        this.draft = draft;
    }

    @Column(name = "water_tank")
    public int getWaterTank() {
        return this.waterTank;
    }

    public void setWaterTank(int waterTank) {
        this.waterTank = waterTank;
    }
    @Column(name = "fuel_tank")
    public int getFuelTank() {
        return this.fuelTank;
    }

    public void setFuelTank(int fuelTank) {
        this.fuelTank = fuelTank;
    }

    @Column(name = "last_checked_date", nullable = false)
    public LocalDate getLastCheckedDate() {
        return this.lastCheckedDate;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

//    @OneToMany(targetEntity = Equipment.class, mappedBy = "id")
//    public List<Equipment> getEquipment() {
//        return this.equipment;
//    }
//
//    public void setEquipment(List<Equipment> equipment) {
//        this.equipment = equipment;
//    }
//
//    @OneToMany(targetEntity = Repair.class, mappedBy = "id")
//    public List<Repair> getRepairs() {
//        return this.repairs;
//    }
//
//    public void setRepairs(List<Repair> repairs) {
//        this.repairs = repairs;
//    }
}





