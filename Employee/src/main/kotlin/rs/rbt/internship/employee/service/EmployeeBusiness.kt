package rs.rbt.internship.employee.service

import org.apache.commons.validator.routines.DateValidator
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.database.service.EmployeeService
import rs.rbt.internship.database.service.UsedVacationService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class EmployeeBusiness {
    @Autowired
    lateinit var employeeService: EmployeeService
    @Autowired
    lateinit var usedVacationService: UsedVacationService

    fun showListRecordsOfUsedVacation(dateStart:String,dateEnd:String,employee_email:String):MutableList<UsedVacation>{
        val emailValidated:Boolean= EmailValidator.getInstance().isValid(employee_email)
        val dateStartValidated:Boolean = DateValidator.getInstance().isValid(dateStart)
        val dateEndValidated:Boolean = DateValidator.getInstance().isValid(dateEnd)

        val employee=employeeService.findEmployeeByEmail(employee_email)

        var lista:MutableList<UsedVacation> = mutableListOf()
        println("proso2 $emailValidated $dateStartValidated $dateEndValidated")
        val formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if(emailValidated){
            if(dateStartValidated && dateEndValidated){
                println("proso")
                val dateStartLocal= LocalDate.parse(dateStart,formatter)
                val dateEndLocal=LocalDate.parse(dateEnd,formatter)
                 lista =  usedVacationService.dates2(dateStartLocal,dateEndLocal,employee)
                //println(lista)
                return lista
            }
            else{
                //exception
                return lista
            }
        }
        else{
            //exception

            return lista
        }
    }
}