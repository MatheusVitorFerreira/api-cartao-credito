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
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("Nenhum funcionário encontrado");
        }
        return employees.stream()
                .map(employee -> {
                    Address address = employee.getAddress();
                    return new EmployeeDTO(employee, address);
                })
                .collect(Collectors.toList());
    }

    public EmployeeDTO findById(String id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Address address = employee.getAddress();
            return new EmployeeDTO(employee, address);
        } else {
            throw new EmployeeNotFoundException("Funcionário não encontrado com o ID: " + id);
        }
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        String name = employeeDTO.getFullName();
        if (employeeRepository.existsByFullName(name)) {
            throw new DuplicateEmployeeException("Funcionário com nome " + name + " já existe");
        }

        String encryptedPassword = passwordEncoder.encode(employeeDTO.getPassword());

        Address address = employeeDTO.getAddress();
        Address savedAddress = addressRepository.save(address);

        Employee employee = employeeDTO.toEmployee();
        employee.setAddress(savedAddress);
        employee.setPassword(encryptedPassword);

        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDTO(savedEmployee, savedAddress);
    }

    public EmployeeDTO update(String id, EmployeeDTO employeeDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            String name = employeeDTO.getFullName();
            if (employeeRepository.existsByFullName(name)) {
                throw new DuplicateEmployeeException("Outro funcionário com nome " + name + " já existe");
            }

            Employee employee = optionalEmployee.get();
            employee.setPosition(employeeDTO.getPosition());
            employee.setSalary(employeeDTO.getSalary());
            employee.setDepartment(employeeDTO.getDepartment());

            Address address = employeeDTO.getAddress();
            if (address != null) {
                Address savedAddress = addressRepository.save(address);
                employee.setAddress(savedAddress);
            }

            Employee updatedEmployee = employeeRepository.save(employee);
            return new EmployeeDTO(updatedEmployee, employee.getAddress());
        } else {
            throw new EmployeeNotFoundException("Funcionário não encontrado com o ID: " + id);
        }
    }

    public void delete(String id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Address address = employee.getAddress();
            if (address != null) {
                addressRepository.delete(address);
            }
            employeeRepository.deleteById(id);
        } else {
            throw new EmployeeNotFoundException("Funcionário não encontrado com o ID: " + id);
        }
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        Address address = employee.getAddress();
        return new EmployeeDTO(employee, address);
    }
}
