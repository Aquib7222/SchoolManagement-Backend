package com.schoolmanagement.schoolmanagementwebsite.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.schoolmanagement.schoolmanagementwebsite.security.JwtFilter;
import com.schoolmanagement.schoolmanagementwebsite.service.UserDetailsServiceImpl;
import org.springframework.security.config.Customizer;

// // @Configuration
// // public class SecurityConfig {
// //     @Autowired
// //     JwtFilter jwtFilter;
// //     @Autowired
// //     UserDetailsServiceImpl userDetailsService;
// //     @Bean
// //     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
// //         return config.getAuthenticationManager();
// //     }
// //     @Bean
// //     public SecurityFilterChain security(HttpSecurity http) throws Exception {
// //         return http
// //             .csrf(cs -> cs.disable())
// //             .authorizeHttpRequests(req -> req
// //                 .requestMatchers("/auth/login").permitAll()
// //                 .requestMatchers("/admin/**").hasRole("admin")
// //                 .requestMatchers("/superadmin/**").hasRole("Superadmin")
// //                 .anyRequest().authenticated()
// //             )
// //             .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
// //             .userDetailsService(userDetailsService)
// //             .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
// //             .build();
// //     }
// //     @Bean
// //     public PasswordEncoder encoder() {
// //         return new BCryptPasswordEncoder();
// //     }
// // }
// @Configuration
// public class SecurityConfig {
//     @Autowired
//     private JwtFilter jwtFilter;
//     @Autowired
//     private UserDetailsServiceImpl userDetailsService;
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
//     @Bean
// public DaoAuthenticationProvider authProvider() {
//     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//     provider.setUserDetailsService(userDetailsService); 
//     provider.setPasswordEncoder(passwordEncoder());
//     return provider;
// }
//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }
//     @Bean
//     public SecurityFilterChain security(HttpSecurity http) throws Exception {
//         http
//             .csrf(cs -> cs.disable())
//             .authorizeHttpRequests(req -> req
//                 .requestMatchers("/auth/login").permitAll()
//                 .requestMatchers("/admin/**").hasRole("ADMIN")
//                 .requestMatchers("/superadmin/**").hasRole("SUPERADMIN")
//                 .anyRequest().authenticated()
//             )
//             .authenticationProvider(authProvider()())     // IMPORTANT
//             .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//         return http.build();
//     }
// }
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import com.schoolmanagement.schoolmanagementwebsite.security.JwtFilter;
// import com.schoolmanagement.schoolmanagementwebsite.service.UserDetailsServiceImpl;
// import org.springframework.security.core.userdetails.UserDetailsService;
// @Configuration
// public class SecurityConfig {
//     @Autowired
//     private JwtFilter jwtFilter;
//     @Autowired
//     private UserDetailsServiceImpl userDetailsService;
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
//     @Bean
// public DaoAuthenticationProvider authProvider() {
//     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//     provider.setUserDetailsService(userDetailsService);   // ✔ Correct setter
//     provider.setPasswordEncoder(passwordEncoder());       // ✔ Add encoder
//     return provider;
// }
//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }
//     @Bean
//     public SecurityFilterChain security(HttpSecurity http) throws Exception {
//         http
//             .csrf(cs -> cs.disable())
//             .authorizeHttpRequests(req -> req
//                 .requestMatchers("/auth/login").permitAll()
//                 .requestMatchers("/admin/**").hasRole("ADMIN")
//                 .requestMatchers("/superadmin/**").hasRole("SUPERADMIN")
//                 .anyRequest().authenticated()
//             )
//             .authenticationProvider(authProvider())   // FIXED ✔
//             .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//         return http.build();
//     }
// }

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider
                = new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder.authenticationProvider(authenticationProvider());
        return authBuilder.build();
    }

 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults()) // <-- IMPORTANT
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/module/**").permitAll()
                .requestMatchers("/api/master/**").permitAll()
                .requestMatchers("/api/menu/**").permitAll()
                .requestMatchers("/api/user-group/**").permitAll()
                .requestMatchers("/api/user-group-mapping/**").permitAll()
                .requestMatchers("/api/school-mapping/**").permitAll()
                .requestMatchers("/api/school/**").permitAll()
                .requestMatchers("/api/superadmin/**").permitAll()
                .requestMatchers("/api/admissions/**").permitAll()
                .requestMatchers("/api/students/**").permitAll()
                .requestMatchers("/api/documents/**").permitAll()
                .requestMatchers("/api/admission-fee/**").permitAll()
                .requestMatchers("/api/sections/**").permitAll()
                .requestMatchers("/api/teachers/**").permitAll()
                .requestMatchers("/api/teacher-attendance/**").permitAll()
                .requestMatchers("/api/fee-master/**").permitAll()
                .requestMatchers("/api/fee-structure/**").permitAll()
                .requestMatchers("/api/student-fee/**").permitAll()
                .requestMatchers("/api/student/attendance/**").permitAll()
                .anyRequest().authenticated()
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
