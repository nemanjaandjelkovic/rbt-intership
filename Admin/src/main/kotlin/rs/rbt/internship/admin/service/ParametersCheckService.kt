package rs.rbt.internship.admin.service

import org.apache.commons.validator.routines.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.ObjectUtils
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.service.EmployeeService

@Service
class ParametersCheckService {
    @Autowired
    lateinit var employeeService: EmployeeService

    fun checkEmail(emailEmployee: String):Boolean {
       return EmailValidator.getInstance().isValid(emailEmployee)
    }
}