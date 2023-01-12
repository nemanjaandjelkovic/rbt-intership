package rs.rbt.internship.admin.dto

import rs.rbt.internship.database.model.Employee

data class EmployeeResponseDto(
    val employee: Employee,
    val message: String
)

