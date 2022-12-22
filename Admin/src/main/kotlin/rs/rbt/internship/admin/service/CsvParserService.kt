package rs.rbt.internship.admin.service

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.database.model.VacationDayPerYear
import rs.rbt.internship.database.service.EmployeeService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class CsvServices() {
    @Autowired
    lateinit var employeeServices: EmployeeService
    @Autowired
    lateinit var adminService: AdminService
    fun uploadCsvEmployee(file: MultipartFile): MutableList<Employee> {

        val fileReader: BufferedReader = BufferedReader(InputStreamReader(file.inputStream, "UTF-8"))
        val csvParser: CSVParser = CSVParser(
            fileReader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withIgnoreEmptyLines()
                .withTrim()
        )

        val employeeMutableList: MutableList<Employee> = mutableListOf()
        val csvRecords: Iterable<CSVRecord> = csvParser.records
        csvRecords.forEach {
            if (it.recordNumber != 1L) {
                val employee: Employee = Employee(
                    email = it.get(0),
                    password = it.get(1)
                )
                employeeMutableList.add(employee)
            }
        }
        return employeeMutableList
    }

    fun uploadCsvUsedVacation(file: MultipartFile): MutableList<UsedVacation> {

        val fileRead: BufferedReader = BufferedReader(InputStreamReader(file.inputStream, "UTF-8"))
        val csvParser: CSVParser = CSVParser(
            fileRead, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
        )
        val usedVacationMutableList: MutableList<UsedVacation> = mutableListOf()
        val csvRecords: Iterable<CSVRecord> = csvParser.records
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
        var day:Int=0
        csvRecords.forEach {
            val usedVacation: UsedVacation = UsedVacation(
                dateStart = LocalDate.parse(it.get(1), formatter),
                dateEnd = LocalDate.parse(it.get(2), formatter),
                employee = employeeServices.findEmployeeByEmail(it.get(0))
            )
            day=adminService.getDaysBetweenDate(usedVacation.dateStart,usedVacation.dateEnd)
            usedVacationMutableList.add(usedVacation)
        }
        return usedVacationMutableList
    }

    fun uploadCsvVacationDayPerYears(file: MutableList<MultipartFile>):MutableList<VacationDayPerYear> {
        var vacationDayPerYearList:MutableList<VacationDayPerYear> = mutableListOf()
        file.forEach {
            val fileRead: BufferedReader = BufferedReader(InputStreamReader(it.inputStream, "UTF-8"))
            val csvParser: CSVParser = CSVParser(
                fileRead, CSVFormat.DEFAULT.withFirstRecordAsHeader()
            )
            val headers: List<String> = csvParser.headerNames
            val csvRecords: Iterable<CSVRecord> = csvParser.records
            val employee: Employee = Employee()
            var vacation: MutableMap<String, Int> = mutableMapOf()
            csvRecords.forEach {
                if (it.recordNumber != 1L) {
                    val vacationDayPerYear:VacationDayPerYear= VacationDayPerYear(year=headers[1],day=Integer.parseInt(it.get(1)), employee = employeeServices.findEmployeeByEmail(it.get(0)))
                    vacationDayPerYearList.add(vacationDayPerYear)
                    employeeServices.saveEmployee(employee)
                }
            }
        }
        return vacationDayPerYearList
    }
}