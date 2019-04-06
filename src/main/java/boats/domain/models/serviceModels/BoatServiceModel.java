package boats.domain.models.serviceModels;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BoatServiceModel extends BaseServiceModel {

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

    public BoatServiceModel() {
    }

    @NotNull
    @Size(min = 2, max = 50)
    public String getProducer() {
        return this.producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @NotNull
    @Size(min = 2, max = 50)
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @NotNull
    @Size(min = 2, max = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCabins() {
        return this.cabins;
    }

    public void setCabins(int cabins) {
        this.cabins = cabins;
    }

    public int getToilets() {
        return this.toilets;
    }

    public void setToilets(int toilets) {
        this.toilets = toilets;
    }

    public double getDraft() {
        return this.draft;
    }

    public void setDraft(double draft) {
        this.draft = draft;
    }

    public int getWaterTank() {
        return this.waterTank;
    }

    public void setWaterTank(int waterTank) {
        this.waterTank = waterTank;
    }

    public int getFuelTank() {
        return this.fuelTank;
    }

    public void setFuelTank(int fuelTank) {
        this.fuelTank = fuelTank;
    }

    public LocalDate getLastCheckedDate() {
        return this.lastCheckedDate;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }

    @NotNull
    @DecimalMin("0.01")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
