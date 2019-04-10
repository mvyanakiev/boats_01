package boats.domain.models.view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CharterHomeViewModel {

    private String boatName;
    private String customerName;
    private String destination;
    private LocalDate endDate;

    public CharterHomeViewModel() {
    }

    public String getBoatName() {
        return this.boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public Date getEndDate() {
    Date date = Date.from(this.endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

