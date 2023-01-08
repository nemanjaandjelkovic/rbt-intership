package rs.rbt.internship.admin.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*


@EnableWebSecurity
@Configuration
class SecurityConfiguration {

    @Bean
    @Throws(Exception::class)
    @Override
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        val apiKeyAuthFilter = ApiKeyAuthFilter()
        http
            .addFilterAfter(ApiKeyAuthFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .csrf().disable()
            .authorizeHttpRequests()
            .anyRequest().authenticated().and().addFilter(ApiKeyAuthFilter()).httpBasic()
        return http.build()
    }
}
