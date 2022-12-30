package rs.rbt.internship.database.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation
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

    fun findByYearAndEmployeeId(year:String,employee:Employee):VacationDayPerYear
    {
        return vacationDayPerYearRepository.findByYearEqualsAndEmployeeIdEquals(year,employee.id)
    }

    fun findAllByEmployeeId(employeeId: Long):MutableList<VacationDayPerYear>
    {
        return vacationDayPerYearRepository.findAllByEmployeeIdEquals(employeeId)
    }

    fun existsVacationDayPerYear(year:String, employeeId:Long):Boolean
    {
        return vacationDayPerYearRepository.existsByYearAndEmployeeId(year, employeeId)
    }

    fun deleteAllVacationDayPerYear()
    {
        vacationDayPerYearRepository.deleteAll()
    }
}