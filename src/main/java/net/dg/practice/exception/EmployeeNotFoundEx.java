package net.dg.practice.exception;

import net.dg.practice.constants.ServiceConstants;

public class EmployeeNotFoundEx extends RuntimeException {

    public EmployeeNotFoundEx() {
        super(ServiceConstants.EMPLOYEE_NOT_FOUND);
    }
}
