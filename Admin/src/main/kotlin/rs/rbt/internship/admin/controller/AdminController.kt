package rs.rbt.internship.admin.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.admin.service.CsvServices
import rs.rbt.internship.database.service.EmployeeServices
import rs.rbt.internship.database.service.UsedVacationServices

@RestController
@RequestMapping("/admin")
class AdminController(var employeeServices: EmployeeServices,
                      var csvServices: CsvServices,
                      var usedVacationServices: UsedVacationServices

)
{

    @PostMapping("/upload/employee")
    fun uploadEmployee(@RequestParam("file") file:MultipartFile) {
        var employeeMutableList:MutableList<Employee> = csvServices.uploadCsvEmployee(file)
        employeeServices.saveEmployees(employeeMutableList)
    }

    @PostMapping("/upload/used-vacation")
    fun uploadUsedVacation(@RequestParam("file") file:MultipartFile) {
        var usedVacationMutableList:MutableList<UsedVacation> = csvServices.uploadCsvUsedVacation(file)
        usedVacationServices.saveUsedVacations(usedVacationMutableList)
    }


    @PostMapping("/upload/vacations")
    fun uploadVacationDayPerYears(@RequestParam("file") file:MutableList<MultipartFile>){
        csvServices.uploadCsvVacationDayPerYears(file)
    }

}