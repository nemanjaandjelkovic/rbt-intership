package rs.rbt.internship.admin.dto

import rs.rbt.internship.database.model.VacationDayPerYear

data class VacationDayPerYearsResponseDto(
    var vacationDayPerYear: VacationDayPerYear,
    var message:String
) {
}