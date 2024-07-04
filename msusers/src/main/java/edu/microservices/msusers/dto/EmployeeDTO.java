package edu.microservices.msusers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.microservices.msusers.domain.Address;
import edu.microservices.msusers.domain.Employee;
import edu.microservices.msusers.domain.TypeEmployee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class EmployeeDTO {

    @NotBlank
    private String fullName;

    @NotBlank
    private String password;

    @NotNull
    private int age;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private TypeEmployee typeEmployee;

    @NotBlank
    private String position;

    @NotNull
    private double salary;

    @NotBlank
    private String department;

    private List<Address> addresses;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Employee employee, List<Address> addresses) {
        this.fullName = employee.getFullName();
        this.password = employee.getPassword();
        this.phoneNumber = employee.getPhoneNumber();
        this.age = employee.getAge();
        this.position = employee.getPosition();
        this.salary = employee.getSalary();
        this.department = employee.getDepartment();
        this.typeEmployee = employee.getTypeEmployee();
        this.addresses = addresses;
    }

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setFullName(this.fullName);
        employee.setPassword(this.password);
        employee.setPhoneNumber(this.phoneNumber);
        employee.setAge(this.age);
        employee.setTypeEmployee(this.typeEmployee);
        employee.setPosition(this.position);
        employee.setSalary(this.salary);
        employee.setDepartment(this.department);
        return employee;
    }
}
