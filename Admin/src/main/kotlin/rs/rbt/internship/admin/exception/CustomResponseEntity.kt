package rs.rbt.internship.admin.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap


open class CustomResponseEntity(status: HttpStatus, val saveObjects: List<Any>?, val objects: List<Any>?) : ResponseEntity<Any>(objects,status)
{

}