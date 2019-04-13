package boats.domain.models.view;

import boats.domain.entities.Boat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EquipmentAllListViewModel {

    private String id;
    private String item;
    private String serialNumber;
    private String boat;
    private LocalDate lastCheckedDate;

    public EquipmentAllListViewModel() {
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

    public String getBoat(Boat boat) {
        return boat.getName();
    }

    public void setBoat(Boat boat) {
        this.boat = boat.getName();
    }

    public Date getLastCheckedDate() {
        if (this.lastCheckedDate != null) {
            Date date = Date.from(this.lastCheckedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return date;
        }
        Date date = Date.from(LocalDate.parse("1900-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}
