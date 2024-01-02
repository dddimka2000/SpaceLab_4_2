package org.example.config;

import groovy.util.logging.Log4j2;
import org.example.service.RecaptchaService;
import org.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@Log4j2
public class WebSecurityConfig {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final
    RecaptchaService recaptchaService;

//    private final ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl,
//                             ClientRegistrationRepository clientRegistrationRepository,
                             RestTemplate restTemplate, RecaptchaService recaptchaService) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
//        this.clientRegistrationRepository = clientRegistrationRepository;
        this.restTemplate = restTemplate;
        this.recaptchaService = recaptchaService;
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    private final
    RestTemplate restTemplate;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeHttpRequests(authorize ->
                        {
                            try {
                                authorize.requestMatchers("/auth/login", "/auth/registration", "/auth/process_login","/img/**","/vendor/**").permitAll()
                                        .requestMatchers("/**", "/personal_account").authenticated()
                                        .anyRequest().permitAll()
                                        .and()
                                        .logout()
                                        .logoutSuccessUrl("/auth/login")
                                        .permitAll();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .formLogin(form ->
                        form.loginPage("/auth/login")
                                .loginProcessingUrl("/auth/process_login")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/auth/login?error")
                )
                .rememberMe()
                .and()
                .httpBasic().disable()
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .defaultAuthenticationEntryPointFor(
                                        new LoginUrlAuthenticationEntryPoint("/auth/login"),
                                        new AntPathRequestMatcher("/**")
                                )
                )
                .addFilterBefore(new CaptchaFilter(recaptchaService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
