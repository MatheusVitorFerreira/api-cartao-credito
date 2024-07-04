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
        return employees.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EmployeeDTO findById(String id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            return convertToDTO(optionalEmployee.get());
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

        List<Address> addresses = employeeDTO.getAddresses();
        if (addresses != null && !addresses.isEmpty()) {
            addresses = addresses.stream().map(addressRepository::save).collect(Collectors.toList());
        }

        Employee employee = employeeDTO.toEmployee();
        employee.setAddresses(addresses);
        employee.setPassword(encryptedPassword);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
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

            List<Address> addresses = employeeDTO.getAddresses();
            if (addresses != null && !addresses.isEmpty()) {
                addresses = addresses.stream().map(addressRepository::save).collect(Collectors.toList());
                employee.setAddresses(addresses);
            }

            Employee updatedEmployee = employeeRepository.save(employee);
            return convertToDTO(updatedEmployee);
        } else {
            throw new EmployeeNotFoundException("Funcionário não encontrado com o ID: " + id);
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
            throw new EmployeeNotFoundException("Funcionário não encontrado com o ID: " + id);
        }
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        List<Address> addresses = addressRepository.findAllById(
                employee.getAddresses().stream().map(Address::getId).collect(Collectors.toList()));
        return new EmployeeDTO(employee, addresses);
    }
}
