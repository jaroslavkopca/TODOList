package app.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for all endpoints with all origins
        registry.addMapping("/api/**")  // or specify specific endpoints like "/api/createTODOEntry"
                .allowedOrigins("http://localhost:5173")  // Allow all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed HTTP methods
                .allowedHeaders("*")  // Allow any headers
                .allowCredentials(true);  // Allow credentials (cookies, etc.)
    }
}
