// package com.schoolmanagement.schoolmanagementwebsite.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {

//         registry
//                 .addResourceHandler("/uploads/**")
//                 .addResourceLocations("file:uploads/");
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String uploadPath = Paths
                .get(System.getProperty("user.dir"), "uploads")
                .toFile()
                .getAbsolutePath();

        System.out.println("UPLOAD PATH = " + uploadPath);

        registry
                .addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}