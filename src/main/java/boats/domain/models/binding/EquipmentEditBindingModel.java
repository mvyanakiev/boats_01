package boats.domain.models.binding;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class EquipmentEditBindingModel {

    private String id;
    private String item;
    private String serialNumber;
    private String boatId;
    private LocalDate lastCheckedDate;

    public EquipmentEditBindingModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @NotEmpty
    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBoatId() {
        return this.boatId;
    }

    public void setBoatId(String boatId) {
        this.boatId = boatId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getLastCheckedDate() {
        return this.lastCheckedDate;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}