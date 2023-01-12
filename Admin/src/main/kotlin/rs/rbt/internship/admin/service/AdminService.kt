package rs.rbt.internship.admin.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.exception.CustomResponseEntity
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

    fun uploadEmployees(file: MultipartFile): CustomResponseEntity {
        val employees: CustomResponseEntity = csvParserService.csvParseEmployee(file)
        employeeService.saveEmployees(employees.saveObjects as MutableList<Employee>)
        return employees

    }

    fun uploadUsedVacations(file: MultipartFile): CustomResponseEntity {
        val usedVacations: CustomResponseEntity = csvParserService.csvParseUsedVacation(file)
        usedVacationService.saveUsedVacations(usedVacations.saveObjects as MutableList<UsedVacation>)
        return usedVacations
    }

    fun uploadVacationDaysPerYear(file: MutableList<MultipartFile>): CustomResponseEntity {
        val vacationDayPerYears: CustomResponseEntity = csvParserService.csvParseVacationDayPerYears(file)
        vacationDayPerYearService.saveVacationDayPerYears(vacationDayPerYears.saveObjects as MutableList<VacationDayPerYear>)
        return vacationDayPerYears

    }

    fun deleteAll() {
        usedVacationService.deleteAllUsedVacation()
        employeeService.deleteAllEmployee()
        vacationDayPerYearService.deleteAllVacationDayPerYear()
    }


}