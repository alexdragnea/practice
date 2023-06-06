package net.dg.practice.rest;

import net.dg.practice.exception.EmployeeNotFoundEx;
import net.dg.practice.model.Employee;
import net.dg.practice.model.response.ResponseBody;
import net.dg.practice.service.EmployeeService;
import net.dg.practice.service.validation.EmployeeValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final EmployeeValidationService employeeValidationService;

    public EmployeeController(EmployeeService employeeService, EmployeeValidationService employeeValidationService) {
        this.employeeService = employeeService;
        this.employeeValidationService = employeeValidationService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return new ResponseEntity<>(employeeService.findAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/paging")
    public ResponseEntity<ResponseBody> getAllEmployeeWithPaging(@RequestParam int page, @RequestParam int size, @RequestParam String order) {
        LOGGER.info("Requesting employees of page {}, size {} and ordering {}", page, size, order);
        return new ResponseEntity<>(employeeService.findEmployeesWithPagination(page, size, order), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        try {
            Employee existingEmployee = employeeService.findEmployeeById(employeeId);
            return new ResponseEntity<>(existingEmployee, HttpStatus.FOUND);
        } catch (EmployeeNotFoundEx ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {

        employeeValidationService.validateBody(employee);
        Employee createdEmployee = employeeService.createEmployee(employee);

        return new ResponseEntity<>(createdEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
        } catch (EmployeeNotFoundEx ex) {
            LOGGER.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeId, @RequestBody Employee updatedEmployee) {
        try {
            employeeService.updateEmployee(updatedEmployee, employeeId);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (EmployeeNotFoundEx ex) {
            LOGGER.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
