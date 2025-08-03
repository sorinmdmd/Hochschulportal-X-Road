package edu.hsbo.hsbobackend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**") // allow all paths
                    .allowedOrigins("http://localhost:4200") // your Angular app URL
                    .allowedMethods("*") // GET, POST, PUT, DELETE, etc.
                    .allowedHeaders("*")
                    .allowCredentials(true)
            }
        }
    }
}