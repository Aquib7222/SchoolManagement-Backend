
// package com.schoolmanagement.schoolmanagementwebsite.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class CorsConfig {

//     @Bean
//     public WebMvcConfigurer corsConfigurer() {

//         return new WebMvcConfigurer() {

//             @Override
//             public void addCorsMappings(CorsRegistry registry) {

//                 registry.addMapping("/**")
//                         .allowedOrigins(
//                                 "http://localhost:5173",
//                                 "http://10.94.128.151:5173",
//                                 "http://localhost:4173",
//                                 "http://10.94.128.151:4173",
//                                 "http://10.199.123.151:5173"
//                         )
//                         .allowedMethods(
//                                 "GET",
//                                 "POST",
//                                 "PUT",
//                                 "PATCH",
//                                 "DELETE",
//                                 "OPTIONS"
//                         )
//                         .allowedHeaders("*")
//                         .allowCredentials(true);
//             }
//         };
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://10.94.128.151:5173",
                "http://localhost:4173",
                "http://10.94.128.151:4173",
                "http://10.199.123.151:5173"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}