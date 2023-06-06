package net.dg.practice.helper;

import net.dg.practice.model.Employee;
import net.dg.practice.model.response.ResponseBody;

import java.util.List;

public class EmployeeObjectMother {

    public static Employee buildEmployee() {
        return new Employee(1L, "Alex", "Dragnea", 5000d, "IT");
    }

    public static List<Employee> buildEmployeeList() {
        return List.of(buildEmployee(), buildEmployee());
    }

    public static ResponseBody buildResponseBody() {
        return new ResponseBody(buildEmployeeList(), 2L);
    }

}
