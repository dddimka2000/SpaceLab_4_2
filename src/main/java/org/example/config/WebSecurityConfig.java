package org.example.config;

import groovy.util.logging.Log4j2;
import org.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class WebSecurityConfig {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, ClientRegistrationRepository clientRegistrationRepository) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .cors().disable()
                .authorizeHttpRequests(authorize ->
                {
                    try {
                        authorize
                                .requestMatchers("/auth/login", "/auth/registration", "/auth/process_login").permitAll()
//                                .requestMatchers("/**").hasAnyAuthority("ADMIN", "MODERATOR")
                                .requestMatchers("/admin/**")
                                .authenticated()
                                .anyRequest().permitAll()
                                .and()
                                .oauth2Login()
                                .clientRegistrationRepository(clientRegistrationRepository)
                                .and()
                                .logout()
                                .logoutSuccessUrl("/")
                                .permitAll();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .formLogin(form ->
                        form.loginPage("/auth/login")
                                .loginProcessingUrl("/auth/process_login")
                                .failureUrl("/auth/login?error"))
                .rememberMe()
                .and()
                .httpBasic();
        return http.build();
    }


}
