package net.dg.practice.service.impl;

import net.dg.practice.exception.EmployeeNotFoundEx;
import net.dg.practice.helper.EmployeeObjectMother;
import net.dg.practice.model.Employee;
import net.dg.practice.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(EmployeeObjectMother.buildEmployeeList());

        List<Employee> expectedEmployees = employeeService.findAllEmployees();

        assertEquals(2, expectedEmployees.size());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(EmployeeObjectMother.buildEmployee()));

        Employee expectedEmployee = employeeService.findEmployeeById(1L);
        assertEquals("Alex", expectedEmployee.getFirstName());
    }

    @Test
    void testGetEmployeeByIdThrowsException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EmployeeNotFoundEx.class, () -> employeeService.findEmployeeById(1L));
    }

    @Test
    void testDeleteEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(EmployeeObjectMother.buildEmployee()));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);

    }

    @Test
    void testDeleteEmployeeByIdThrowsException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EmployeeNotFoundEx.class, () -> employeeService.deleteEmployee(1L));
    }

    @Test
    void testUpdateEmployee() {

        Employee updatedEmployee = new Employee(1L, "Jane", "Smith", 6000.0, "Sales");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(EmployeeObjectMother.buildEmployee()));
        when(employeeRepository.save(any(Employee.class))).thenReturn(EmployeeObjectMother.buildEmployee());

        employeeService.updateEmployee(updatedEmployee, 1L);

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));

        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(argumentCaptor.capture());
        Employee capturedEmployee = argumentCaptor.getValue();
        
        assertEquals(updatedEmployee.getFirstName(), capturedEmployee.getFirstName());
        assertEquals(updatedEmployee.getLastName(), capturedEmployee.getLastName());
        assertEquals(updatedEmployee.getDepartment(), capturedEmployee.getDepartment());
        assertEquals(updatedEmployee.getSalary(), capturedEmployee.getSalary());
    }


}