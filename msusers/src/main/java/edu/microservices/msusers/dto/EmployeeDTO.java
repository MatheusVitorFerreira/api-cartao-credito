package edu.microservices.msusers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.microservices.msusers.domain.Address;
import edu.microservices.msusers.domain.Employee;
import lombok.Data;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class EmployeeDTO {
    private String fullName;
    private String password;
    private int age;
    private String phoneNumber;
    private String id;
    private String position;
    private double salary;
    private String department;
    private List<Address> addresses;

    public EmployeeDTO() {}

    public EmployeeDTO(Employee employee, List<Address> addresses) {
        this.fullName = employee.getFullName();
        this.password = employee.getPassword();
        this.phoneNumber = employee.getPhoneNumber();
        this.age = employee.getAge();
        this.id = employee.getId();
        this.position = employee.getPosition();
        this.salary = employee.getSalary();
        this.department = employee.getDepartment();
        this.addresses = addresses;
    }

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setFullName(this.fullName);
        employee.setPassword(this.password);
        employee.setPhoneNumber(this.phoneNumber);
        employee.setAge(this.age);
        employee.setId(this.id);
        employee.setPosition(this.position);
        employee.setSalary(this.salary);
        employee.setDepartment(this.department);
        return employee;
    }
}
