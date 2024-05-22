package com.project.edentifica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //Acepta todas las llamadas de la web
        registry.addMapping("/edentifica/**")
                .allowedOrigins("https://edentifica.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);


//        registry.addMapping("/edentifica/users/getbyphonenumber/**")
//                .allowedOrigins("http://162.211.82.148/~edentifica")
//                .allowedMethods("GET", "POST")
//                .allowCredentials(true);
//
//        registry.addMapping("/edentifica/users/get_by_email/**")
//                .allowedOrigins("https://162.211.82.148/~edentifica")
//                .allowedMethods("GET", "POST")
//                .allowCredentials(true);
//
//        registry.addMapping("/edentifica/users/getbytypeandsocialnetwork/**")
//                .allowedOrigins("http://162.211.82.148/~edentifica")
//                .allowedMethods("GET", "POST")
//                .allowCredentials(true);
    }
}
