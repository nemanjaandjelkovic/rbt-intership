package rs.rbt.internship.employee.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.employee.service.EmployeeBusinessService

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
        dateEnd: String,
        @RequestParam(name = "employeeEmail")
        employeeEmail: String
    ): MutableList<UsedVacation> {
        return employeeBusinessService.showListRecordsOfUsedVacation(dateStart, dateEnd, employeeEmail)
    }

    @PostMapping("/addVacation")
    fun addVacation(
        @RequestParam dateStart: String,
        @RequestParam dateEnd: String,
        @RequestParam(name = "employeeEmail") employeeEmail: String
    ) {
        employeeBusinessService.addVacation(dateStart,dateEnd,employeeEmail)
    }

    @GetMapping("")
    fun employeeInfo( @RequestParam(name = "employeeEmail") employeeEmail: String):MutableMap<String,MutableList<Int>>
    {
      return employeeBusinessService.employeeInfo(employeeEmail)
    }

}