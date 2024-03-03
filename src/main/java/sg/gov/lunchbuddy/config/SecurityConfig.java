package sg.gov.lunchbuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationFilter filter) throws Exception {

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.securityMatcher("/**")
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers("/auth/login").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .anyRequest().authenticated())
                .csrf().disable()
                .cors().disable()
                .build();
    }
}
