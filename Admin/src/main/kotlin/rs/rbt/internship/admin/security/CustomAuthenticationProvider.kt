//package rs.rbt.internship.admin.security
//
//import org.springframework.security.authentication.AuthenticationProvider
//import org.springframework.security.authentication.BadCredentialsException
//import org.springframework.security.authentication.InsufficientAuthenticationException
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.AuthenticationException
//import org.springframework.stereotype.Component
//import org.springframework.util.ObjectUtils
//
//
//@Component
//class CustomAuthenticationProvider : AuthenticationProvider {
//    @Throws(AuthenticationException::class)
//    override fun authenticate(authentication: Authentication): Authentication? {
//        val apiKey = authentication.principal as String
//        if (ObjectUtils.isEmpty(apiKey)) {
//            throw InsufficientAuthenticationException("No API key in request")
//        } else {
//            if ("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6IkFkbWluIiwicGFzc3dvcmQiOiJBZG1pbjEyM0FkbWluIn0.m7IRPmq2BVjU_w0fRY8JbUKj2ZcdT76JVc91jZ2qunE" == apiKey) {
//                return ApiKeyAuthenticationToken(apiKey, true)
//            }
//            throw BadCredentialsException("API Key is invalid")
//        }
//    }
//
//    override fun supports(authentication: Class<*>?): Boolean {
//        return ApiKeyAuthenticationToken::class.java.isAssignableFrom(authentication)
//    }
//
//}