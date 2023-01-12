package rs.rbt.internship.admin.exception

import org.springframework.http.HttpStatus

class CustomException(val status: HttpStatus, override val message: String, val objects: List<Any>?) : Exception()