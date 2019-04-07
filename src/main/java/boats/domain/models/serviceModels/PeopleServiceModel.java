package boats.domain.models.serviceModels;

import javax.validation.constraints.NotNull;

public class PeopleServiceModel extends BaseServiceModel {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private boolean isEmployee;
    private boolean isCustomer;
    private boolean isSupplier;

    public PeopleServiceModel() {
    }

    @NotNull
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @NotNull
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEmployee() {
        return this.isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public boolean isCustomer() {
        return this.isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    public boolean isSupplier() {
        return this.isSupplier;
    }

    public void setSupplier(boolean supplier) {
        isSupplier = supplier;
    }
}
