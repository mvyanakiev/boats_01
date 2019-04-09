package boats.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PeopleAddBindingModel {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private boolean isEmployee;
    private boolean isCustomer;
    private boolean isSupplier;

    public PeopleAddBindingModel() {
    }

    @NotNull
    @NotEmpty
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

    @NotNull
    @NotEmpty
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NotNull
    @NotEmpty
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

    public boolean getIsEmployee() {
        return this.isEmployee;
    }

    public void setIsEmployee(boolean employee) {
        isEmployee = employee;
    }

    public boolean getIsCustomer() {
        return this.isCustomer;
    }

    public void setIsCustomer(boolean customer) {
        isCustomer = customer;
    }

    public boolean getIsSupplier() {
        return this.isSupplier;
    }

    public void setIsSupplier(boolean supplier) {
        isSupplier = supplier;
    }
}
