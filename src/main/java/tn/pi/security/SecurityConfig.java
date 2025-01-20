package tn.pi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import tn.pi.entities.Company;
import tn.pi.repos.CompanyRepo;

@Configuration
public class SecurityConfig {

    private final CompanyRepo companyRepo;

    public SecurityConfig(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if ("admin".equals(username)) {
                String encodedPassword = passwordEncoder().encode("123");
                return User.withUsername("admin").password(encodedPassword).roles("ADMIN").build();
            } else {
                Company company = companyRepo.findByUsername(username);
                if (company == null) {
                    throw new UsernameNotFoundException("User not found");
                }
                return User.withUsername(company.getUsername())
                        .password(company.getPassword())
                        .roles("COMPANY")  // Correct role assignment with "COMPANY"
                        .build();
            }
        };
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .formLogin(Customizer.withDefaults())  // Default login page
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/dashboard").hasRole("ADMIN")  // Only ADMIN can access /dashboard
                                .requestMatchers("/company/**").hasRole("COMPANY")  // Only COMPANY can access company dashboard
                                .requestMatchers("/jobs", "/").permitAll()  // Allow all users to access jobs and home page
                                .anyRequest().permitAll()  // Allow other requests
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

