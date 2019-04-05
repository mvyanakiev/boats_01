package boats.domain.models.serviceModels;

import boats.domain.entities.Boat;

import java.time.LocalDate;

public class EquipmentServiceModel extends BaseServiceModel {

    private String item;
    private String serialNumber;
    private Boat boat;
    private LocalDate lastCheckedDate;

    public EquipmentServiceModel() {
    }

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

    public Boat getBoat() {
        return this.boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public LocalDate getLastCheckedDate() {
        return this.lastCheckedDate;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}
