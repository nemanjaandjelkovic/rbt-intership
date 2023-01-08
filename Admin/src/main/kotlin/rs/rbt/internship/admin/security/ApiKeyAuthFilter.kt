package rs.rbt.internship.admin.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class ApiKeyAuthFilter : OncePerRequestFilter() {
    companion object {
        private const val HEADER_NAME = "API-Key"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val apiKey = request.getHeader(HEADER_NAME)
        println(isValidApiKey(apiKey))
        if (!isValidApiKey(apiKey)) {
            return response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key")
        }

        return filterChain.doFilter(request, response)
    }


    private fun isValidApiKey(apiKey: String?): Boolean {
        return apiKey.equals("abc123")
    }

}
