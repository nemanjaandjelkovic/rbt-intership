package rs.rbt.internship.admin.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.util.*


@EnableWebSecurity
@Configuration
class SecurityConfiguration {

    var apiKeyAuthFilter:ApiKeyAuthFilter = ApiKeyAuthFilter()
    @Bean
    @Throws(Exception::class)
    @Override
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {

        http
            .csrf().disable()
            .addFilterBefore(ApiKeyAuthFilter(), BasicAuthenticationFilter::class.java)
        return http.build()
    }


}
