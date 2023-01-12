package rs.rbt.internship.admin.extenstion

import rs.rbt.internship.admin.dto.EmployeeResponseDto
import rs.rbt.internship.admin.dto.UsedVacationResponseDto
import rs.rbt.internship.admin.dto.VacationDayPerYearsResponseDto
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.database.model.VacationDayPerYear

fun Employee.toResponse(employee: Employee, message: String): EmployeeResponseDto {
    return EmployeeResponseDto(this,message)
}

fun UsedVacation.toResponse(usedVacation: UsedVacation, message: String): UsedVacationResponseDto {
    return UsedVacationResponseDto(usedVacation,message)
}

fun VacationDayPerYear.toResponse(vacationDayPerYear: VacationDayPerYear, message: String): VacationDayPerYearsResponseDto {
    return VacationDayPerYearsResponseDto(vacationDayPerYear,message)
}
