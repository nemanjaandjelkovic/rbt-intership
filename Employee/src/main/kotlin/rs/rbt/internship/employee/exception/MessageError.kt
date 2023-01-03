package rs.rbt.internship.employee.exception

enum class MessageError(val message:String) {
    WrongDate("INVALID RANGE OF DATE SECOND DATE IS SMALLER THEN FIRST"),
    DaysOut("YOU DON'T HAVE A SINGLE DAY OFF"),
    InvalidParameters("PARAMETERS IS NOT GOOD")

}