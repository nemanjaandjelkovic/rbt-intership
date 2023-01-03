package rs.rbt.internship.admin.service

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import rs.rbt.internship.admin.exception.CsvColumnName
import rs.rbt.internship.admin.exception.CsvMessageError
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.database.model.VacationDayPerYear
import rs.rbt.internship.database.service.EmployeeService
import rs.rbt.internship.database.service.UsedVacationService
import rs.rbt.internship.database.service.VacationDayPerYearService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class CsvParserService {
    @Autowired
    lateinit var employeeServices: EmployeeService

    @Autowired
    lateinit var usedVacationDayPerYearService: VacationDayPerYearService

    var usedVacationDaysService: UsedVacationDaysService = UsedVacationDaysService()

    @Autowired
    lateinit var parametersCheckService: ParametersCheckService

    @Autowired
    lateinit var usedVacationService: UsedVacationService


    //TODO:
    // VRACA JSON OBJEKTE KOJI NISU PROSLI

    fun csvParseEmployee(file: MultipartFile): MutableList<Employee> {

        val fileReader = BufferedReader(InputStreamReader(file.inputStream, "UTF-8"))
        val csvParser = CSVParser(
            fileReader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withIgnoreEmptyLines()
                .withTrim()
        )
        val headers: List<String> = csvParser.headerNames

        val employees: MutableList<Employee> = mutableListOf()
        val csvRecords: Iterable<CSVRecord> = csvParser.records


        csvRecords.forEach {
            if (it.recordNumber != 1L) {
                if (parametersCheckService.checkEmail(it.get(0))) {
                    if (!employeeServices.employeeExists(it.get(0))) {
                        val employee: Employee = Employee(
                            email = it.get(0),
                            password = it.get(1)
                        )
                        employees.add(employee)
                    } else {
                        throw ResponseStatusException(
                            HttpStatus.NOT_ACCEPTABLE,
                            CsvMessageError.EmployeeExists.message
                        )
                    }
                } else {
                    throw ResponseStatusException(
                        HttpStatus.PARTIAL_CONTENT,
                        CsvMessageError.WrongEmailFormat.message
                    )
                }
            } else {
                if (it.get(0) != CsvColumnName.Email.columnName && it.get(1) != CsvColumnName.Password.columnName) {
                    return throw ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE, CsvMessageError.WrongCsv.message
                    )
                }
            }
        }
        return employees
    }
        //PROVERA DATUMA IZ USED VACATION formatter
    fun csvParseUsedVacation(file: MultipartFile): MutableList<UsedVacation> {

        val fileRead = BufferedReader(InputStreamReader(file.inputStream, "UTF-8"))
        val csvParser = CSVParser(
            fileRead, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
        )
        val usedVacations: MutableList<UsedVacation> = mutableListOf()
        val csvRecords: Iterable<CSVRecord> = csvParser.records
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
        var day: MutableMap<String, Int>
        if (csvParser.headerNames.size != 3) {
            return throw ResponseStatusException(
                HttpStatus.NOT_ACCEPTABLE, CsvMessageError.WrongCsv.message
            )
        }
        if (csvParser.headerNames[0] != CsvColumnName.Employee.columnName && csvParser.headerNames[1] != CsvColumnName.StartDate.columnName && csvParser.headerNames[2] != CsvColumnName.EndDate.columnName) {
            return throw ResponseStatusException(
                HttpStatus.NOT_ACCEPTABLE, CsvMessageError.WrongCsv.message
            )
        }
        csvRecords.forEach { it ->
            if(it.size()!=3){
                return throw ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, CsvMessageError.WrongCsvRow.message
                )
            }
            if (LocalDate.parse(it.get(1), formatter) > LocalDate.parse(it.get(2), formatter)) {
                return throw ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, CsvMessageError.WrongDate.message
                )
            }
            // if employee exists
            if (employeeServices.employeeExists(it.get(0)) && parametersCheckService.checkEmail(it.get(0))) {
                val usedVacation = UsedVacation(
                    dateStart = LocalDate.parse(it.get(1), formatter),
                    dateEnd = LocalDate.parse(it.get(2), formatter),
                    employee = employeeServices.findEmployeeByEmail(it.get(0))
                )
                // if usedVacation exists (if not do =>)
                if (!usedVacationService.existsUsedVacation(
                        usedVacation.dateStart,
                        usedVacation.dateEnd,
                        usedVacation.employee.email
                    )
                ) {
                    day = usedVacationDaysService.getDaysBetweenDate(usedVacation.dateStart, usedVacation.dateEnd)
                    var yearDayLeft: VacationDayPerYear

                    day.forEach {
                        try {
                            yearDayLeft =
                                usedVacationDayPerYearService.findByYearAndEmployeeId(it.key, usedVacation.employee)
                            if (yearDayLeft.day - it.value >= 0 && it.key == yearDayLeft.year) {
                                usedVacations.add(usedVacation)
                                usedVacationDayPerYearService.updateVacationDayPerYears(
                                    it.value,
                                    it.key,
                                    usedVacation.employee
                                )
                            }
                        } catch (e: Exception) {

                        }
                    }
                } else {
                    return throw ResponseStatusException(
                        HttpStatus.PARTIAL_CONTENT,
                        CsvMessageError.UsedVacationExists.message
                    )
                }
            } else {
                return throw ResponseStatusException(
                    HttpStatus.NOT_FOUND, CsvMessageError.NotFoundEmployee.message
                )
            }
        }
        return usedVacations
    }


    fun csvParseVacationDayPerYears(file: MutableList<MultipartFile>): MutableList<VacationDayPerYear> {
        val vacationDayPerYears: MutableList<VacationDayPerYear> = mutableListOf()
        file.forEach { it ->
            val fileRead = BufferedReader(InputStreamReader(it.inputStream, "UTF-8"))
            val csvParser = CSVParser(
                fileRead, CSVFormat.DEFAULT.withFirstRecordAsHeader()
            )
            val headers: List<String> = csvParser.headerNames
            val csvRecords: Iterable<CSVRecord> = csvParser.records
            val employee = Employee()
            csvRecords.forEach {
                if (it.recordNumber != 1L) {
                    //if employee exists and year have 4 char
                    if (employeeServices.employeeExists(it.get(0)) && parametersCheckService.checkEmail(it.get(0)) && headers[1].length == 4) {
                        val vacationDayPerYear: VacationDayPerYear = VacationDayPerYear(
                            year = headers[1],
                            day = Integer.parseInt(it.get(1)),
                            employee = employeeServices.findEmployeeByEmail(it.get(0))
                        )
                        //if usedVacationDayPerYear don't exist
                        if (!usedVacationDayPerYearService.existsVacationDayPerYear(
                                vacationDayPerYear.year,
                                vacationDayPerYear.employee.id
                            )
                        ) {
                            vacationDayPerYears.add(vacationDayPerYear)
                            employeeServices.saveEmployee(employee)
                        } else {
                            throw ResponseStatusException(
                                HttpStatus.BAD_REQUEST, CsvMessageError.VacationDaysPerYearExists.message
                            )
                        }
                    } else {
                        throw ResponseStatusException(
                            HttpStatus.NOT_FOUND, CsvMessageError.NotFoundEmployee.message
                        )
                    }
                } else {
                    if (it.get(0) != CsvColumnName.Employee.columnName && it.get(1) != CsvColumnName.TotalVacationDays.name) {
                        return throw ResponseStatusException(
                            HttpStatus.NOT_ACCEPTABLE, CsvMessageError.WrongCsv.message
                        )
                    }
                }
            }
        }
        return vacationDayPerYears
    }


}