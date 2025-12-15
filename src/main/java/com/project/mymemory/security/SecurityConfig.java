package com.project.mymemory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();

                    // Allow cors origin only with these port
                    config.addAllowedOrigin("http://localhost:3000");
                     config.addAllowedOrigin("http://localhost:5173");

                    // Allow GET, POST, PUT, DELETE, OPTIONS
                    String[] allowedMethods = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
                    for (String method : allowedMethods) {
                        config.addAllowedMethod(method);
                    }

                    // Allow all headers
                    config.addAllowedHeader("*");
                    config.addExposedHeader("*");

                    // Allow cookies / auth headers
                    config.setAllowCredentials(true);

                    return config;
                }))


                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers("/api/users/**").permitAll()
                        .anyRequest().permitAll()
//                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
