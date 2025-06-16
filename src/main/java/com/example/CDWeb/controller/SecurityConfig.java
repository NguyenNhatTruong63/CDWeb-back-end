package com.example.CDWeb.controller;

import com.example.CDWeb.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) 
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/category").permitAll()
                        .requestMatchers("/api/product/**").permitAll()
                        .requestMatchers("/api/logout").permitAll()
                        .requestMatchers("/api/product/random").permitAll()
                        .requestMatchers("/api/comment/product/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/getEmail").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/resetPasswordEmail").permitAll()

                        .requestMatchers("/api/comment/product/").permitAll()

                        //unauthorized
                        //get
                        .requestMatchers(HttpMethod.GET, "/api/category/{id}").authenticated()

                        //post
                        .requestMatchers(HttpMethod.POST, "/api/category/add").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/cart/user").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/cart/decrease").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/cart/add").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/user/add2").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/user/resetPassword").authenticated()



                        .requestMatchers(HttpMethod.POST, "/api/comment/add").authenticated()

                        //put
                        .requestMatchers(HttpMethod.PUT, "/api/user/update/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/comment/update/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/category/update/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/product/update/{id}").authenticated()

                        //delete
                        .requestMatchers(HttpMethod.DELETE, "/api/comment/delete/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/category/delete/{categoryName}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/product/delete/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/cart/delete").authenticated()

                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // ✅ Frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // Nếu dùng Authorization

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            // Trả về UserDetails chứ không phải entity User của bạn
//            return new User(
//                    username,
//                    passwordEncoder().encode("dummyPassword"), // mã hoá
//                    List.of(new SimpleGrantedAuthority("ROLE_USER")) // role
//            );
//        };
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



