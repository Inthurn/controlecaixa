package br.com.inthurn.backend.auth.configuration;

import br.com.inthurn.backend.auth.UserAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserAuthenticationFilter USER_AUTHENTICATION_FILTER;

    public SecurityConfiguration(UserAuthenticationFilter USER_AUTHENTICATION_FILTER) {
        this.USER_AUTHENTICATION_FILTER = USER_AUTHENTICATION_FILTER;
    }

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/users",
            "/users/login",
    };

    private static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/cash-balance",
            "/cash-balance/*",
            "/cash-movement",
            "/cash-movement/*"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        try {
            return httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                            .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                            .anyRequest().denyAll()
                    )
                    .addFilterBefore(USER_AUTHENTICATION_FILTER, UsernamePasswordAuthenticationFilter.class)
                    .build();

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}