package com.jgg2425.da.fa2.finalactivity2.config;

import com.jgg2425.da.fa2.finalactivity2.models.dao.ISellerDAO;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.apache.commons.codec.digest.DigestUtils;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ISellerDAO sellerDAO;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtils.md5Hex(rawPassword.toString()).toUpperCase();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return cif -> {
            Seller seller = sellerDAO.findByCif(cif)
                    .orElseThrow(() -> new UsernameNotFoundException("Seller cif not found"));

            return User.withUsername(seller.getCif())
                    .password(seller.getPassword())
                    .roles("SELLER")
                    //.passwordEncoder(passwordEncoder::encode)
                    // comment this line if password is stored encoded
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .requiresChannel( channel -> channel.anyRequest().requiresSecure() )
                .authorizeHttpRequests(auth -> auth
                        // free access to the REST API
                        .requestMatchers("/api-rest/**").permitAll()
                        // free access to the styles.css file
                        .requestMatchers("/styles.css").permitAll()
                        .requestMatchers("/sAppLoginStyles.css").permitAll()
                        .requestMatchers("/sellerAppStyles.css").permitAll()
                        // free access to login page
                        .requestMatchers("/login").permitAll()
                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/seller_data", true)
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)  // Invalidates user's session
                        .clearAuthentication(true)  // Clears session's authentication
                )
                .httpBasic(withDefaults());

        return http.build();
    }
}
