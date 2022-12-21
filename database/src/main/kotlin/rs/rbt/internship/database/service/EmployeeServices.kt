package rs.rbt.internship.database.service

import org.springframework.stereotype.Service
import rs.rbt.internship.database.repository.EmployeeRepository

@Service
class EmployeeServices(var employeeRepository: EmployeeRepository) {
}