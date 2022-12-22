package rs.rbt.internship.admin.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.service.CsvParserService
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.database.model.VacationDayPerYear
import rs.rbt.internship.database.service.EmployeeService
import rs.rbt.internship.database.service.UsedVacationService
import rs.rbt.internship.database.service.VacationDayPerYearService

@RestController
@RequestMapping("/admin")
class AdminController(){
    @Autowired
    lateinit var csvParserService: CsvParserService
    @Autowired
    lateinit var employeeService: EmployeeService
    @Autowired
    lateinit var usedVacationService: UsedVacationService
    @Autowired
    lateinit var vacationDayPerYearService: VacationDayPerYearService


    @PostMapping("/upload/employee")
    fun uploadEmployee(@RequestParam("file") file: MultipartFile) {
        val employeeMutableList: MutableList<Employee> = csvParserService.uploadCsvEmployee(file)
        employeeService.saveEmployees(employeeMutableList)
    }

    @PostMapping("/upload/used-vacation")
    fun uploadUsedVacation(@RequestParam("file") file: MultipartFile) {
        val usedVacationMutableList: MutableList<UsedVacation> = csvParserService.uploadCsvUsedVacation(file)
        usedVacationService.saveUsedVacations(usedVacationMutableList)
    }

    @PostMapping("/upload/vacations")
    fun uploadVacationDaysPerYear(@RequestParam("file") file: MutableList<MultipartFile>) {
        val vacationDayPerYears:MutableList<VacationDayPerYear> = csvParserService.uploadCsvVacationDayPerYears(file)
        vacationDayPerYearService.saveVacationDayPerYears(vacationDayPerYears)
    }

}