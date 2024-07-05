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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private String idEmployee;
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

    private Address address;

    public EmployeeDTO(Employee employee, Address address) {
        this.idEmployee = employee.getIdEmployee(); // Atribuir corretamente o ID do Employee
        this.fullName = employee.getFullName();
        this.password = employee.getPassword();
        this.phoneNumber = employee.getPhoneNumber();
        this.age = employee.getAge();
        this.typeEmployee = employee.getTypeEmployee();
        this.position = employee.getPosition();
        this.salary = employee.getSalary();
        this.department = employee.getDepartment();
        this.address = address;
    }

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setIdEmployee(this.idEmployee);
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
