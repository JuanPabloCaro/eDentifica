package com.project.edentifica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/edentifica/users/getbyphonenumber/**")
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST")
                .allowCredentials(true);

        registry.addMapping("/edentifica/users/getbyemailname/**")
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST")
                .allowCredentials(true);

        registry.addMapping("/edentifica/users/getbytypeandsocialnetwork/**")
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST")
                .allowCredentials(true);
    }
}
