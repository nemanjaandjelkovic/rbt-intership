package rs.rbt.internship.employee.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.employee.service.EmployeeBusiness

@RestController
@RequestMapping("/employee")
class EmployeeController {
    @Autowired
    lateinit var employeeBusiness: EmployeeBusiness

    @GetMapping("/listVacation")
    @ResponseBody
    fun listOfVacationDates(@RequestParam dateStart:String,@RequestParam dateEnd:String,@RequestParam(name="email") employee_email:String):MutableList<UsedVacation>{
        return employeeBusiness.showListRecordsOfUsedVacation(dateStart,dateEnd,employee_email)

    }

}