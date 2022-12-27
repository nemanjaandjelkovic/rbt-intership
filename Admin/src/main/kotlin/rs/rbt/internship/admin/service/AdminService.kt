package rs.rbt.internship.admin.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.database.model.VacationDayPerYear
import rs.rbt.internship.database.service.EmployeeService
import rs.rbt.internship.database.service.UsedVacationService
import rs.rbt.internship.database.service.VacationDayPerYearService

@Service
class AdminService {
    @Autowired
    lateinit var csvParserService: CsvParserService
    @Autowired
    lateinit var employeeService: EmployeeService
    @Autowired
    lateinit var usedVacationService: UsedVacationService
    @Autowired
    lateinit var vacationDayPerYearService: VacationDayPerYearService

    fun uploadEmployees(file:MultipartFile){
        val employees: MutableList<Employee> = csvParserService.csvParseEmployee(file)
        employeeService.saveEmployees(employees)
    }

    fun uploadUsedVacations(file:MultipartFile){
        val usedVacations: MutableList<UsedVacation> = csvParserService.csvParseUsedVacation(file)
        usedVacationService.saveUsedVacations(usedVacations)
    }
    fun uploadVacationDaysPerYear(file: MutableList<MultipartFile>) {
        val vacationDayPerYears:MutableList<VacationDayPerYear> = csvParserService.csvParseVacationDayPerYears(file)
        vacationDayPerYearService.saveVacationDayPerYears(vacationDayPerYears)
    }



}