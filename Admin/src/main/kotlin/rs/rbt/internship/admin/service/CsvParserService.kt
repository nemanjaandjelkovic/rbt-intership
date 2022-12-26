package rs.rbt.internship.admin.service

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
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
    //PROVERA CSV PODATKA PRE RADA!!!!!
    @Autowired
    lateinit var employeeServices: EmployeeService

    @Autowired
    lateinit var usedVacationDayPerYearService: VacationDayPerYearService

    @Autowired
    lateinit var usedVacationDaysService: UsedVacationDaysService

    @Autowired
    lateinit var parametersCheckService: ParametersCheckService

    @Autowired
    lateinit var usedVacationService: UsedVacationService
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
                if (parametersCheckService.checkEmail(it.get(0))) {
                    if (!employeeServices.employeeExists(it.get(0))) {
                        val employee: Employee = Employee(
                            email = it.get(0),
                            password = it.get(1)
                        )
                        employeeMutableList.add(employee)
                    } else {
                        throw ResponseStatusException(
                            HttpStatus.NOT_ACCEPTABLE, "Employee vec postoji, ostali koji ne postoje su uneti"
                        )
                    }
                } else {
                    throw ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE, "Postoji problem sa formatom maila, ostalo koji su dobri su uneti"
                    )
                }

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
        var day: MutableMap<String, Int> = mutableMapOf()
        csvRecords.forEach { it ->
            // try {
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
                    var yearDayLeft = VacationDayPerYear()

                    day.forEach {
                        try {
                            yearDayLeft =
                                usedVacationDayPerYearService.findByYearAndEmployeeId(it.key, usedVacation.employee)
                            if (yearDayLeft.day - it.value >= 0 && it.key == yearDayLeft.year) {
                                usedVacationMutableList.add(usedVacation)
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
                    throw ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE, "Vec postoji odmor"
                    )
                }
            } else {
                throw ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, "Ne postoji taj employee"
                )
            }
        }

        return usedVacationMutableList

    }


    fun uploadCsvVacationDayPerYears(file: MutableList<MultipartFile>): MutableList<VacationDayPerYear> {
        val vacationDayPerYearList: MutableList<VacationDayPerYear> = mutableListOf()
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
                    if (employeeServices.employeeExists(it.get(0)) && parametersCheckService.checkEmail(it.get(0)) && headers[1].length==4) {
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
                            vacationDayPerYearList.add(vacationDayPerYear)
                            employeeServices.saveEmployee(employee)
                        } else {
                            throw ResponseStatusException(
                                HttpStatus.NOT_ACCEPTABLE, "Vec postoji zapis za tu godinu"
                            )
                        }
                    } else {
                        throw ResponseStatusException(
                            HttpStatus.NOT_ACCEPTABLE, "Ne postoji taj employee"
                        )
                    }
                }
            }
        }
        return vacationDayPerYearList
    }


}