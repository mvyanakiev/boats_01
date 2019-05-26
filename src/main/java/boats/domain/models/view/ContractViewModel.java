package boats.domain.models.view;


import boats.domain.models.serviceModels.EquipmentServiceModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContractViewModel {

    private String boatName;
    private String boatModel;
    private String boatProducer;
    private String customerName;
    private String destination;
    private String startDate;
    private String today;
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

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getToday() {
        return this.today;
    }

    public void setToday(String today) {
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
