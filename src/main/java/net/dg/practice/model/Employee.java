package net.dg.practice.model;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Employee extends Person {

    private Double salary;
    private String department;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, Double salary, String department) {
        super(id, firstName, lastName);
        this.salary = salary;
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getSalary(), employee.getSalary()) && Objects.equals(getDepartment(), employee.getDepartment()) && Objects.equals(getId(), employee.getId()) && Objects.equals(getFirstName(), employee.getFirstName()) && Objects.equals(getLastName(), employee.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSalary(), getDepartment());
    }
}
