package rs.rbt.internship.admin.exception

enum class CsvColumnName(val columnName:String) {
    EMAIL("Employee Email"),
    PASSWORD("Employee Password"),
    START_DATE("Vacation start date"),
    END_DATE("Vacation end date"),
    EMPLOYEE("Employee"),
    TOTAL_VACATION_DAYS("Total vacation days")
}