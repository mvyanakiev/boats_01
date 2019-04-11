package boats.domain.models.view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class BoatHomeViewModel {

    private String id;
    private String boatName;
    private LocalDate lastCheckedDate;

    public BoatHomeViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoatName() {
        return this.boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public Date getLastCheckedDate() {
        Date date = Date.from(this.lastCheckedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}
