package rs.rbt.internship.admin.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap


class CustomResponseEntity( status: HttpStatus, val message: String, val objects: List<Any>?) : ResponseEntity<Any>(objects,status)
{

}