package rs.rbt.internship.database.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.VacationDayPerYear
import rs.rbt.internship.database.repository.VacationDayPerYearRepository

@Service
class VacationDayPerYearService {
    @Autowired
    lateinit var vacationDayPerYearRepository:VacationDayPerYearRepository
    fun saveVacationDayPerYears(vacationDayPerYear: MutableList<VacationDayPerYear>)
    {
        vacationDayPerYearRepository.saveAll(vacationDayPerYear)
    }
    fun updateVacationDayPerYears(days:Int,year:String,employee:Employee)
    {
        vacationDayPerYearRepository.updateVacationDayPerYear(days,year,employee.id)
    }
}