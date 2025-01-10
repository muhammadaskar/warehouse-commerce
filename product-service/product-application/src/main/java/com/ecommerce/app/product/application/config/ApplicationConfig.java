package com.ecommerce.app.product.application.config;

import com.ecommerce.app.product.application.middleware.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    public ApplicationConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/products/**")
                .excludePathPatterns();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/products/**")
                .allowedOrigins("http://localhost:3000")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
}
