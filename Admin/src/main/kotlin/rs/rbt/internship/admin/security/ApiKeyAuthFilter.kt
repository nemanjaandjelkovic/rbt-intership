package rs.rbt.internship.admin.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class ApiKeyAuthFilter : GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        val response = response as HttpServletResponse
        val request: HttpServletRequest = request as HttpServletRequest
        val apiKey = request.getHeader("API-Key")


        if (!isValidApiKey(apiKey)) {
            return response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key")
        }
        else{
            val auth = UsernamePasswordAuthenticationToken("Admin", "pass", mutableListOf() )
            SecurityContextHolder.getContext().authentication = auth
            return filterChain.doFilter(request, response)
        }
        return filterChain.doFilter(request, response)
    }
    private fun isValidApiKey(apiKey: String?): Boolean {
        return apiKey.equals("abc123")
    }
}