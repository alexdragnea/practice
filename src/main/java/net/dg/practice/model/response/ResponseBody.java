package net.dg.practice.model.response;

import net.dg.practice.model.Employee;

import java.util.List;

public class ResponseBody {

    private List<Employee> employeeList;
    private Long totalResults;

    public ResponseBody(List<Employee> employeeList, Long totalResults) {
        this.employeeList = employeeList;
        this.totalResults = totalResults;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }
}
