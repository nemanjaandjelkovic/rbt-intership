package rs.rbt.internship.admin.exception

enum class CsvColumnName(val columnName:String) {
    Email("Employee Email"),
    Password("Employee Password"),
    StartDate("Vacation start date"),
    EndDate("Vacation end date"),
    Employee("Employee"),
    TotalVacationDays("Total vacation days")
}