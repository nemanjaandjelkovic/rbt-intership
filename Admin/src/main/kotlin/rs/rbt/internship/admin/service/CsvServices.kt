package rs.rbt.internship.admin.service

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.database.service.EmployeeServices
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class CsvServices(var employeeServices: EmployeeServices) {
    fun uploadCsvEmployee(file: MultipartFile): MutableList<Employee> {

        var fileRead: BufferedReader = BufferedReader(InputStreamReader(file.inputStream, "UTF-8"))
        var csvParser: CSVParser = CSVParser(
            fileRead, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
        )

        var employeeMutableList: MutableList<Employee> = mutableListOf()
        var csvRecords: Iterable<CSVRecord> = csvParser.records
        csvRecords.forEach {
            if (it.recordNumber != 1L) {
                var employee: Employee = Employee(
                    email = it.get(0),
                    password = it.get(1)
                )
                employeeMutableList.add(employee)
            }
        }
        return employeeMutableList
    }

    fun uploadCsvUsedVacation(file: MultipartFile): MutableList<UsedVacation> {

        var fileRead: BufferedReader = BufferedReader(InputStreamReader(file.inputStream, "UTF-8"))
        var csvParser: CSVParser = CSVParser(
            fileRead, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
        )
        var usedVacationMutableList: MutableList<UsedVacation> = mutableListOf()
        var csvRecords: Iterable<CSVRecord> = csvParser.records
        var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
        csvRecords.forEach {
            var usedVacation: UsedVacation = UsedVacation(
                dateStart = LocalDate.parse(it.get(1), formatter),
                dateEnd = LocalDate.parse(it.get(2), formatter),
                employee = employeeServices.findEmployeeByEmail(it.get(0))
            )
            usedVacationMutableList.add(usedVacation)
        }
        return usedVacationMutableList
    }

    fun uploadCsvVacationDayPerYears(file: MutableList<MultipartFile>) {
        file.forEach {
            var fileRead: BufferedReader = BufferedReader(InputStreamReader(it.inputStream, "UTF-8"))
            var csvParser: CSVParser = CSVParser(
                fileRead, CSVFormat.DEFAULT.withFirstRecordAsHeader()
            )
            var headers: List<String> = csvParser.headerNames
            var csvRecords: Iterable<CSVRecord> = csvParser.records
            var employee: Employee = Employee()
            var vacation: MutableMap<String, Int> = mutableMapOf()
            csvRecords.forEach {
                if (it.recordNumber != 1L) {
                    vacation = employeeServices.findEmployeeByEmail(it.get(0)).vacationDayPerYear
                    vacation[headers[1]] = Integer.parseInt(it.get(1))
                    employee = employeeServices.findEmployeeByEmail(it.get(0))
                    employee.vacationDayPerYear = vacation
                    employeeServices.saveEmployee(employee)
                }
            }
        }
    }
}