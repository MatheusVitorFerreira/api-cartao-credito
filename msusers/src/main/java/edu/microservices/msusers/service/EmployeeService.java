package edu.microservices.msusers.service;

import edu.microservices.msusers.domain.Address;
import edu.microservices.msusers.domain.Employee;
import edu.microservices.msusers.dto.EmployeeDTO;
import edu.microservices.msusers.repository.AddressRepository;
import edu.microservices.msusers.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll().stream().map(employee -> {
            List<Address> addresses = addressRepository.findAllById(employee.getAddresses().stream().map(Address::getId).collect(Collectors.toList()));
            return new EmployeeDTO(employee, addresses);
        }).collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> findById(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            List<Address> addresses = addressRepository.findAllById(employee.get().getAddresses().stream().map(Address::getId).collect(Collectors.toList()));
            return Optional.of(new EmployeeDTO(employee.get(), addresses));
        }
        return Optional.empty();
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        List<Address> addresses = employeeDTO.getAddresses();
        if (addresses != null) {
            addresses = addresses.stream().map(addressRepository::save).collect(Collectors.toList());
        }

        Employee employee = employeeDTO.toEmployee();
        employee.setAddresses(addresses);
        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDTO(savedEmployee, addresses);
    }

    public EmployeeDTO update(String id, EmployeeDTO employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setPosition(employeeDetails.getPosition());
            employee.setSalary(employeeDetails.getSalary());
            employee.setDepartment(employeeDetails.getDepartment());

            List<Address> addresses = employeeDetails.getAddresses();
            if (addresses != null) {
                addresses = addresses.stream().map(addressRepository::save).collect(Collectors.toList());
                employee.setAddresses(addresses);
            }

            Employee updatedEmployee = employeeRepository.save(employee);
            return new EmployeeDTO(updatedEmployee, addresses);
        } else {
            throw new RuntimeException("Employee not found with id " + id);
        }
    }

    public void delete(String id) {
        employeeRepository.deleteById(id);
    }
}
