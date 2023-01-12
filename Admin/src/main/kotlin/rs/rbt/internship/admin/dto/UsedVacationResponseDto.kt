package rs.rbt.internship.admin.dto

import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation

data class UsedVacationResponseDto(
    val usedVacation: UsedVacation,
    val message: String
) {
}

