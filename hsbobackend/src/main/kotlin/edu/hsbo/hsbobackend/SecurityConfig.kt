package edu.hsbo.hsbobackend
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    // Permit Swagger UI and API docs without authentication
                    .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                    ).permitAll()
                    // All other endpoints require authentication
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { it.jwt() }

        return http.build()
    }
}
