//package com.example.th.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
////import com.example.th.config.JwtRequestFilter; // Assume you have this filter for JWT
//import com.example.th.service.CustomUserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter; // Assume you have implemented this filter
//
//    @SuppressWarnings("deprecation") 
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//            .authorizeRequests(authorize -> authorize
//                    .requestMatchers("/emp/**").authenticated() // Require authentication for /emp/ endpoints
//                    .anyRequest().permitAll()) // Allow other requests without authentication
//            .csrf(csrf -> csrf.disable());
//            // Add the JWT request filter
//            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Disable CSRF for stateless applications (e.g., REST APIs)
//        return http.build();
//    }
//        
//        
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // Password encoder bean
//    }
//
//    @Bean
//    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager(); // Provide AuthenticationManager
//    }
//}

package com.example.th.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.th.filter.JwtFilter;
//import com.example.th.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;
    
    

//    @SuppressWarnings("deprecation")
//	@Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless for REST APIs
//            .authorizeRequests(authorize -> authorize
//                .requestMatchers("/emp/superadmin/**").hasAuthority("SUPER_ADMIN")
//                .requestMatchers("/emp/admin/**").hasAuthority("ADMIN")
//                .requestMatchers("/emp/employees/**").hasAuthority("EMPLOYEE")
//                .requestMatchers("/emp/**").authenticated() // Require authentication for all /emp endpoints
//                .anyRequest().permitAll() // Permit all other requests
//            )
//            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless applications
//            .exceptionHandling(exceptions -> exceptions // Customize error handling
//                .authenticationEntryPoint((request, response, authException) -> {
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//                })
//            );
//        
//
//        // Add the JWT request filter
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        
//        return http.build();
//    }
//    
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless for REST APIs
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/emp/superadmin/**").hasAuthority("SUPER_ADMIN")  // Automatically applies to /emp/superadmin/ because /emp is the context path
                .requestMatchers("/emp/admin/**").hasAuthority("ADMIN")              // Automatically applies to /emp/admin/
                .requestMatchers("/emp/employees/**").hasAuthority("EMPLOYEE")       // Automatically applies to /emp/employees/
                .requestMatchers("/emp/**").authenticated() // Requires authentication for all paths under /emp/ 
                .anyRequest().permitAll() // Permit all other requests
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless applications
            .exceptionHandling(exceptions -> exceptions // Customize error handling
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                })
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before UsernamePasswordAuthenticationFilter

        return http.build();
    }

    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder bean
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager(); // Provide AuthenticationManager
    }
    
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
