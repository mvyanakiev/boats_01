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
    private PeopleType type;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public PeopleType getType() {
        return this.type;
    }

    public void setType(PeopleType type) {
        this.type = type;
    }
}
