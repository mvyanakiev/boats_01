package boats.domain.models.view;

import boats.domain.entities.Boat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EquipmentViewModel {

    private String id;
    private String item;
    private String serialNumber;
    private Boat boat;
    private LocalDate lastCheckedDate;

    public EquipmentViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getLastCheckedDate() {
        Date date = Date.from(this.lastCheckedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}
