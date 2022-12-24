package rs.rbt.internship.admin.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.service.AdminService
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
    lateinit var adminService: AdminService


    @PostMapping("/upload/employee")
    fun uploadEmployee(@RequestParam("file") file: MultipartFile) {
        adminService.UploadEmployees(file)
    }

    @PostMapping("/upload/used-vacation")
    fun uploadUsedVacation(@RequestParam("file") file: MultipartFile) {
        adminService.UploadUsedVacations(file)
    }

    @PostMapping("/upload/vacations")
    fun uploadVacationDaysPerYear(@RequestParam("file") file: MutableList<MultipartFile>) {
       adminService.uploadVacationDaysPerYear(file)
    }

}