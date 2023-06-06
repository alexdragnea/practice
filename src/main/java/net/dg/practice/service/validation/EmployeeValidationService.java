package net.dg.practice.service.validation;

import net.dg.practice.constants.ServiceConstants;
import net.dg.practice.exception.ValidationException;
import net.dg.practice.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmployeeValidationService {

    public void validateBody(Employee employee) throws ValidationException {

        if (!StringUtils.hasLength(employee.getFirstName()) || !StringUtils.hasLength(employee.getLastName()) || !StringUtils.hasLength(employee.getDepartment())) {
            throw new ValidationException(String.format(ServiceConstants.VALIDATION_FORMAT, ServiceConstants.FIELD_MANDATORY));
        }

    }
}

