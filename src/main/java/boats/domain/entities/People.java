package boats.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "peoples")
public class People extends BaseEntity {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private boolean isEmployee;
    private boolean isCustomer;
    private boolean isSupplier;


    public People() {
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "address")
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "is_employee")
    public boolean isEmployee() {
        return this.isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    @Column(name = "is_customer")
    public boolean isCustomer() {
        return this.isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    @Column(name = "is_supplier")
    public boolean isSupplier() {
        return this.isSupplier;
    }

    public void setSupplier(boolean supplier) {
        isSupplier = supplier;
    }
}