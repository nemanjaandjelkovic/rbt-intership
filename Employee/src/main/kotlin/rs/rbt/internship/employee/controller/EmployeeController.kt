package rs.rbt.internship.employee.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.employee.service.EmployeeBusinessService
import org.springframework.security.core.*


@RestController
@RequestMapping("/employee")
class EmployeeController {
    @Autowired
    lateinit var employeeBusinessService: EmployeeBusinessService


    @GetMapping("/listVacation")
    @ResponseBody
    fun listOfVacationDates(
        @RequestParam
        dateStart: String,
        @RequestParam
        dateEnd: String
    ): MutableList<UsedVacation> {
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        return employeeBusinessService.showListRecordsOfUsedVacation(dateStart, dateEnd, auth.name)
    }

    @PostMapping("/addVacation")
    fun addVacation(
        @RequestParam
        dateStart: String,
        @RequestParam
        dateEnd: String
    ) {
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        employeeBusinessService.addVacation(dateStart, dateEnd, auth.name)
    }

    @GetMapping("")
    fun employeeInfo(year:String): MutableMap<String, MutableList<Int>> {
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        return employeeBusinessService.employeeInfo(auth.name,year)
    }

}