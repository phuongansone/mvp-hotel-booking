package com.hotel.mvp.hotelbooking.config;

import com.hotel.mvp.hotelbooking.security.JwtAuthenticationManager;
import com.hotel.mvp.hotelbooking.security.JwtAuthorizationFilter;
import com.hotel.mvp.hotelbooking.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config ->
                        config
                            .requestMatchers("/api/bookings/**").authenticated()
                            .requestMatchers("/api/users/register").permitAll()
                            .requestMatchers("/api/auth/login").permitAll()
                            .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthorizationFilter(authenticationManagerBean(), jwtUtil),
                        UsernamePasswordAuthenticationFilter.class
                );

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new JwtAuthenticationManager(userDetailsService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
