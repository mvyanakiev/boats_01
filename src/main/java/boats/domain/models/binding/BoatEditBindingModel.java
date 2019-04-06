package boats.domain.models.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BoatEditBindingModel {

    private String id;
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

    public BoatEditBindingModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @NotEmpty
    @Length(min = 2, message = "Producer must be at least 2 characters long.")
    @Length(max = 50, message = "Producer must be at least 50 characters long.")
    public String getProducer() {
        return this.producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @NotNull
    @NotEmpty
    @Length(min = 2, message = "Model must be at least 2 characters long.")
    @Length(max = 50, message = "Model must be at least 50 characters long.")
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @NotNull
    @NotEmpty
    @Length(min = 2, message = "Name must be at least 2 characters long.")
    @Length(max = 50, message = "Name must be at least 50 characters long.")
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

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getLastCheckedDate() {
        return this.lastCheckedDate;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }

    @NotNull
//    @NotEmpty
    @DecimalMin("0.01")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
