package boats.domain.models.view;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class BoatDetailsViewModel {

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

    public BoatDetailsViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProducer() {
        return this.producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

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

    public Date getLastCheckedDate() {
        Date date = Date.from(this.lastCheckedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
