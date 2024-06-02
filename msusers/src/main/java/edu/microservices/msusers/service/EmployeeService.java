package edu.microservices.msusers.service;

import edu.microservices.msusers.domain.Address;
import edu.microservices.msusers.domain.Employee;
import edu.microservices.msusers.dto.EmployeeDTO;
import edu.microservices.msusers.exception.erros.DuplicateEmployeeException;
import edu.microservices.msusers.exception.erros.EmployeeNotFoundException;
import edu.microservices.msusers.repository.AddressRepository;
import edu.microservices.msusers.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll().stream().map(employee -> {
            List<Address> addresses = addressRepository
                    .findAllById(employee
                            .getAddresses()
                            .stream()
                            .map(Address::getId)
                            .collect(Collectors.toList()));
            return new EmployeeDTO(employee, addresses);
        }).collect(Collectors.toList());
    }

    public EmployeeDTO findById(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            List<Address> addresses = addressRepository.findAllById(employee.get()
                    .getAddresses()
                    .stream()
                    .map(Address::getId)
                    .collect(Collectors.toList()));
            return new EmployeeDTO(employee.get(), addresses);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id " + id);
        }
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        String name = employeeDTO.getFullName();
        if (employeeRepository.existsByFullName(name)) {
            throw new DuplicateEmployeeException("Employee with name " + name + " already exists");
        }

        String encryptedPassword = encryptPassword(employeeDTO.getPassword());

        List<Address> addresses = employeeDTO.getAddresses();
        if (addresses != null) {
            addresses = addresses.stream().map(addressRepository::save).collect(Collectors.toList());
        }

        Employee employee = employeeDTO.toEmployee();
        employee.setAddresses(addresses);
        employee.setPassword(encryptedPassword);
        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDTO(savedEmployee, addresses);
    }

    public EmployeeDTO update(String id, EmployeeDTO object) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            String name = object.getFullName();
            if (employeeRepository.existsByFullNameAndIdNot(name, id)) {
                throw new DuplicateEmployeeException("Another employee with name " + name + " already exists");
            }

            Employee employee = optionalEmployee.get();
            employee.setPosition(object.getPosition());
            employee.setSalary(object.getSalary());
            employee.setDepartment(object.getDepartment());

            List<Address> addresses = object.getAddresses();
            if (addresses != null) {
                addresses = addresses.stream().map(addressRepository::save).collect(Collectors.toList());
                employee.setAddresses(addresses);
            }

            Employee updatedEmployee = employeeRepository.save(employee);
            return new EmployeeDTO(updatedEmployee, addresses);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id " + id);
        }
    }

    public void delete(String id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Address> addresses = employee.getAddresses();
            if (addresses != null && !addresses.isEmpty()) {
                for (Address address : addresses) {
                    addressRepository.delete(address);
                }
            }
            employeeRepository.deleteById(id);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id " + id);
        }
    }
    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

