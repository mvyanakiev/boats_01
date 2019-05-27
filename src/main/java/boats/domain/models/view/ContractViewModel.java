package boats.domain.models.view;


import boats.domain.models.serviceModels.EquipmentServiceModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContractViewModel {

    private String boatName;
    private String boatModel;
    private String boatProducer;
    private String customerName;
    private String direction;
    private LocalDate startDate;
    private String period;
    private LocalDate today;
    private String price;
    private List<EquipmentServiceModel> equipment;

    public ContractViewModel() {
        this.equipment = new ArrayList<>();
    }

    public String getBoatName() {
        return this.boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public String getBoatModel() {
        return this.boatModel;
    }

    public void setBoatModel(String boatModel) {
        this.boatModel = boatModel;
    }

    public String getBoatProducer() {
        return this.boatProducer;
    }

    public void setBoatProducer(String boatProducer) {
        this.boatProducer = boatProducer;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Date getStartDate() {
        Date date = Date.from(this.startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getToday() {
        Date date = Date.from(this.today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<EquipmentServiceModel> getEquipment() {
        return this.equipment;
    }

    public void setEquipment(List<EquipmentServiceModel> equipment) {
        this.equipment = equipment;
    }
}
