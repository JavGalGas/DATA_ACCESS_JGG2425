package com.jgg2425.da.unit5.springemployeeexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    // Setting up users in memory
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // Creating users with encrypted passwords
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder.encode("password1"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("password2"))
                .roles("USER", "ADMIN")
                .build());
        manager.createUser(User.withUsername("bdadmin")
                .password(passwordEncoder.encode("password3"))
                .roles("USER", "BDADMIN")
                .build());

        return manager;
    }

    // Bean for password hacker
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Setting up the safety chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Force HTTPS for all requests
            .requiresChannel(channel -> channel.anyRequest().requiresSecure())

            // Request Authorization Settings
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api-rest/**").permitAll() // Free access to the REST API
                    .requestMatchers("/employee-app/**").permitAll() // Free access to REST API redirection
                    .requestMatchers("/styles.css").permitAll() // Free access to styles
                    .requestMatchers("/login").permitAll() // Free access to the login page
                    .anyRequest().authenticated() // Any other request requires authentication
            )

            // Login form settings
            .formLogin(form -> form
                    .loginPage("/login") // Custom login page (Must create login HTML page)
                    .defaultSuccessUrl("/index", true) // Redirection after successful login
                    .permitAll() // Allow access to the login form
            )

            // Logout settings
            .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                    .logoutSuccessUrl("/login?logout") // Redirection after logout (Must control that with ViewController)
                    .invalidateHttpSession(true) // Invalidate session
                    .deleteCookies("JSESSIONID") // Clear session cookies
                    .permitAll()
            )

            // Enable Basic Authentication (Optional)
            .httpBasic(withDefaults());

        return http.build();
    }
}