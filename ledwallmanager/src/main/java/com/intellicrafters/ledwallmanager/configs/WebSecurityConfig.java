package com.intellicrafters.ledwallmanager.configs;

import com.intellicrafters.ledwallmanager.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfig   {

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.headers(headers -> headers.disable());
        http.authenticationProvider(authenticationProvider());
        // metto in sicurezza users e la richiesta di id per impianti
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users").authenticated()
                        .requestMatchers("/report/**").authenticated()
                        .requestMatchers("/gestioneImpianti/**").authenticated() // mette in sicurezza le richieste di ledwall
                        .requestMatchers("/ledwall/**").permitAll() // mette in sicurezza le richieste di ledwall
                                .anyRequest().permitAll()
                )
                .formLogin(login ->
                        login.usernameParameter("email")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll()
                );

        return http.build();
    }

}