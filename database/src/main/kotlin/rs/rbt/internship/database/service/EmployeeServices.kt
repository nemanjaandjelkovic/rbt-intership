package rs.rbt.internship.database.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.repository.EmployeeRepository

@Service
class EmployeeServices(var employeeRepository: EmployeeRepository) {

    fun findEmployeeByEmail(email: String): Employee {
        return employeeRepository.findEmployeeByEmail(email)
    }
    fun saveEmployees(employees: MutableList<Employee>) {
        employeeRepository.saveAll(employees)
    }

    fun saveEmployee(employee: Employee)
    {
        employeeRepository.save(employee)
    }

}