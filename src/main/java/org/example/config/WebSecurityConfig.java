package org.example.config;

import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;
import org.example.service.RecaptchaService;
import org.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final CaptchaFilter captchaFilter;
    private final RecaptchaService recaptchaService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(customizer -> customizer
//                        .requestMatchers("/admin/*", "*/admin/*").authenticated()
                        .requestMatchers("/admin/*", "*/admin/*").permitAll()
                        .requestMatchers("/auth/login", "/auth/registration", "/auth/process_login").permitAll()
                        .anyRequest().permitAll()
                )
//                .oauth2Login(configurer -> configurer.
//                                            clientRegistrationRepository(clientRegistrationRepository))
                .formLogin(configurer -> configurer
                                        .loginPage("/auth/login")
//                                        .loginProcessingUrl("/auth/process_login")
                                        .failureUrl("/auth/login?error"))
                .logout()
                .and()
                .rememberMe()
                .and()
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
