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
        val employeeMutableList: MutableList<Employee> = csvParserService.uploadCsvEmployee(file)
        employeeService.saveEmployees(employeeMutableList)
    }

    fun uploadUsedVacations(file:MultipartFile){
        val usedVacationMutableList: MutableList<UsedVacation> = csvParserService.uploadCsvUsedVacation(file)
        usedVacationService.saveUsedVacations(usedVacationMutableList)
    }
    fun uploadVacationDaysPerYear(file: MutableList<MultipartFile>) {
        val vacationDayPerYears:MutableList<VacationDayPerYear> = csvParserService.uploadCsvVacationDayPerYears(file)
        vacationDayPerYearService.saveVacationDayPerYears(vacationDayPerYears)
    }



}