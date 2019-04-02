package boats.domain.models.view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class BoatAllViewModel {

    private String id;
    private String name;
    private String producer;
    private String model;
    private LocalDate lastCheckedDate;

    public BoatAllViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getLastCheckedDate() {
        Date date = Date.from(this.lastCheckedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public void setLastCheckedDate(LocalDate lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}
