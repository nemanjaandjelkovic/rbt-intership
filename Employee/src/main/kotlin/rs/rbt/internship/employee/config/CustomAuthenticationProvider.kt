package rs.rbt.internship.employee.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import rs.rbt.internship.database.service.EmployeeService

@Component
class CustomAuthenticationProvider:AuthenticationProvider {
    @Autowired
    lateinit var employeeService: EmployeeService
    override fun authenticate(authentication: Authentication?): Authentication? {

        val name:String = authentication?.name.toString()
        val password:String= authentication?.credentials.toString()
        return if(employeeService.employeeEx(name,password)) {
            UsernamePasswordAuthenticationToken(name,password, mutableListOf())
        } else{
            null
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.equals(UsernamePasswordAuthenticationToken::class.java)!!
        //?. ako nije null pozovi equals (!! da sigurno nije null)
        //ako jeste ne radi nista
    }
}