package rs.rbt.internship.admin.exception

import org.springframework.http.HttpStatus

class CsvException(val status: HttpStatus, message: String, val responseList: List<Any>): RuntimeException(message) {

}