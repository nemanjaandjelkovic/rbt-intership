//package rs.rbt.internship.admin.security
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.web.SecurityFilterChain
//import java.util.*
//
//
//@EnableWebSecurity
//@Configuration
//class SecurityConfiguration {
//
//
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
//        http.csrf().disable()
//            .authorizeHttpRequests { auth ->
//                auth.anyRequest().authenticated()
//            }
//            .httpBasic()
//        return http.build()
//    }
//}