package net.dg.practice.service;

import net.dg.practice.model.Employee;
import net.dg.practice.model.response.ResponseBody;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployees();

    ResponseBody findEmployeesWithPagination(int pageNumber, int size, String order);

    Employee findEmployeeById(Long id);

    Employee createEmployee(Employee employee);

    void deleteEmployee(Long id);

    void updateEmployee(Employee employee, Long id);

}
