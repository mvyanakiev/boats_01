package boats.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "boats")
public class Boat extends BaseEntity {

    private String producer;
    private String model;
    private int cabins;
    private int toilets;
    private double draft;
    private double overallLength;
    private int waterTank;
    private int fuelTank;
    private LocalDate lastCheckedDate;
    private BigDecimal price;
    private boolean isPresent;
    private List<Equipment> equipment;

    public Boat() {
    }

    //todo

}





