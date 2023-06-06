package net.dg.practice.rest;

import net.dg.practice.exception.EmployeeNotFoundEx;
import net.dg.practice.helper.EmployeeObjectMother;
import net.dg.practice.service.impl.EmployeeServiceImpl;
import net.dg.practice.service.validation.EmployeeValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;
    @MockBean
    private EmployeeValidationService employeeValidationService;

    @Test
    void testGetAllEmployees() throws Exception {
        when(employeeService.findAllEmployees()).thenReturn(EmployeeObjectMother.buildEmployeeList());

        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/api/v1/employees")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].department").isNotEmpty())
                .andExpect(jsonPath("$.[0].salary").isNotEmpty());
    }

    @Test
     void testGetAllEmployeesWithPaging() throws Exception {
        when(employeeService.findEmployeesWithPagination(anyInt(), anyInt(), anyString())).thenReturn(EmployeeObjectMother.buildResponseBody());

        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/api/v1/employees/paging")
                                .param("page", "1")
                                .param("size", "10")
                                .param("order", "ASC")
                                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());
    }

    @Test
    void testGetEmployeeByIdThrowsException() throws Exception {
        when(employeeService.findEmployeeById(anyLong())).thenThrow(EmployeeNotFoundEx.class);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/employees/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}