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

@Service
class EmployeeBusinessService {
    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var usedVacationService: UsedVacationService
    @Autowired
    lateinit var usedVacationDaysService: UsedVacationDaysService

    fun showListRecordsOfUsedVacation(
        dateStart: String,
        dateEnd: String,
        employeeEmail: String
    ): MutableList<UsedVacation> {

        var usedVacations: MutableList<UsedVacation> = mutableListOf()
        val dateStartEnd: MutableList<LocalDate> = convertParameters(dateStart, dateEnd)

        if (parametersValid(dateStart, dateEnd, employeeEmail)) {
            val employee = employeeService.findEmployeeByEmail(employeeEmail)
            usedVacations = usedVacationService.dates(dateStartEnd[0], dateStartEnd[1], employee.id)
        } else {
            //exception
        }
        return usedVacations
    }

    fun parametersValid(
        dateStart: String,
        dateEnd: String,
        employeeEmail: String
    ): Boolean {
        val emailValidated: Boolean = EmailValidator.getInstance().isValid(employeeEmail)
        val dateStartValidated: Boolean = DateValidator.getInstance().isValid(dateStart)
        val dateEndValidated: Boolean = DateValidator.getInstance().isValid(dateEnd)
        if (employeeService.findEmployeeByEmail(employeeEmail) != null) {
            return emailValidated && dateEndValidated && dateStartValidated
        } else {
            println("uslo")
            return false
        }
    }

    fun convertParameters(
        dateStart: String,
        dateEnd: String
    ): MutableList<LocalDate> {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")
        val dates: MutableList<LocalDate> = mutableListOf()
        dates.add(LocalDate.parse(dateStart, formatter))
        dates.add(LocalDate.parse(dateEnd, formatter))

        return dates
    }

    fun addVacation(
        dateStart: String,
        dateEnd: String,
        employeeEmail: String
    ) {
        if (parametersValid(dateStart, dateEnd, employeeEmail)) {
            val dateStartEnd: MutableList<LocalDate> = convertParameters(dateStart, dateEnd)
             usedVacationDaysService.getDaysBetweenDate(dateStartEnd[0], dateStartEnd[1])
        }
    }
}