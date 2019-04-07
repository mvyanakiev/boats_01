package boats.domain.models.serviceModels;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class DirectionServiceModel extends BaseServiceModel {

    private String destination;
    private int distance;
    private int period;
    private BigDecimal price;

    public DirectionServiceModel() {
    }

    @NotNull
    @Size(min = 2, max = 50, message = "Destination must be between 2 and 50 letters")
    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    @Min(value = 1, message = "Minimal period is 1 day")
    @Max(value = 30, message = "Maximal period is 30 days")
    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @NotNull
    @DecimalMin("0.01")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
