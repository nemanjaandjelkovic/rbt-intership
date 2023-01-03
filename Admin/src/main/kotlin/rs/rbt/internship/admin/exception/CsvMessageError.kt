package rs.rbt.internship.admin.exception

enum class CsvMessageError(val message:String) {
    WrongCsv("WRONG CSV FILE"),
    WrongDateFormat("INVALID DATE FORMAT MUST BE IN DD/MM/YYYY"),
    WrongDate("INVALID RANGE OF DATE SECOND DATE IS SMALLER THEN FIRST"),
    WrongCsvRow("WRONG ROW IN CSV FILE"),
    NotFoundEmployee("EMPLOYEE IS NOT FOUND"),
    WrongEmailFormat("FORMAT FOR EMAIL IS NOT GOOD"),
    EmployeeExists("EMPLOYEE EXISTS"),
    VacationDaysPerYearExists("VACATION DAY PER YEAR ALREADY EXISTS"),
    UsedVacationExists("VACATION ALREADY EXISTS")

}