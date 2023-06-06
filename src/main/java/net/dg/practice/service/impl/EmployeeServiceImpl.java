package net.dg.practice.service.impl;

import net.dg.practice.exception.EmployeeNotFoundEx;
import net.dg.practice.model.Employee;
import net.dg.practice.model.response.ResponseBody;
import net.dg.practice.repository.EmployeeRepository;
import net.dg.practice.service.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public ResponseBody findEmployeesWithPagination(int pageNumber, int size, String order) {
        if (order.equalsIgnoreCase("DESC")) {
            return new ResponseBody(employeeRepository.findAll(PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "id"))).getContent(),
                    employeeRepository.count());
        }

        return new ResponseBody(employeeRepository.findAll(PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "id"))).getContent(),
                employeeRepository.count());
    }

    @Override
    public Employee findEmployeeById(Long id) throws EmployeeNotFoundEx {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundEx());
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) throws EmployeeNotFoundEx {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            employeeRepository.deleteById(id);
        } else
            throw new EmployeeNotFoundEx();
    }

    @Override
    public void updateEmployee(Employee employee, Long id) throws EmployeeNotFoundEx {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundEx()
        );

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());
        employeeRepository.save(existingEmployee);
    }
}
