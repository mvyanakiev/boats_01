package boats.domain.models.binding;

import boats.domain.entities.Boat;
import boats.domain.entities.Direction;
import boats.domain.entities.People;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CharterAddBindingModel {

    private String boatId;
    private LocalDate startDate;
    private BigDecimal price;
    private String customerId;
    private String directionId;

    public CharterAddBindingModel() {
    }

    public String getBoatId() {
        return this.boatId;
    }

    public void setBoatId(String boatId) {
        this.boatId = boatId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDirectionId() {
        return this.directionId;
    }

    public void setDirectionId(String directionId) {
        this.directionId = directionId;
    }
}
