package boats.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    private People people;
    private String position;
    private BigDecimal salary;
    private String note;

    public Employee() {
    }

    @OneToOne
    @JoinColumn(name = "people_id")
    public People getPeople() {
        return this.people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    @Column(name = "position", nullable = false)
    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name = "salary")
    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Column(name = "note")
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
