package com.example.icoach.config;

import com.example.icoach.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(userDetailsService)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/home", "/about", "/programs/**", "/events/**",
                    "/team", "/news/**", "/contact", "/donate", "/volunteer",
                    "/login", "/register", "/error",
                    "/static/**", "/css/**", "/js/**", "/images/**",
                    "/api/newsletter/**", "/api/contact/**",
                    "/h2-console/**", "/favicon.ico"
                ).permitAll()
                .requestMatchers("/admin/donations/**", "/admin/reports/**")
                    .hasAnyRole("SUPER_ADMIN", "ADMIN", "FINANCE")
                .requestMatchers("/admin/volunteers/**")
                    .hasAnyRole("SUPER_ADMIN", "ADMIN", "VOLUNTEER_COORDINATOR")
                .requestMatchers("/admin/news/**")
                    .hasAnyRole("SUPER_ADMIN", "ADMIN", "COMMUNICATIONS")
                .requestMatchers("/admin/programs/**")
                    .hasAnyRole("SUPER_ADMIN", "ADMIN", "PROGRAM_MANAGER")
                .requestMatchers("/admin/users/**")
                    .hasAnyRole("SUPER_ADMIN")
                .requestMatchers("/admin/**")
                    .hasAnyRole("SUPER_ADMIN", "ADMIN", "FINANCE", "PROGRAM_MANAGER",
                                 "VOLUNTEER_COORDINATOR", "COMMUNICATIONS")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin/dashboard", true)
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**", "/api/**")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }
}
