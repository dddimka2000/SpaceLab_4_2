package org.example.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.dto.RecaptchaResponse;
import org.example.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
public class CaptchaFilter extends OncePerRequestFilter {
    private final RecaptchaService recaptchaService;

    public CaptchaFilter(RecaptchaService recaptchaService) {
        this.recaptchaService = recaptchaService;
    }

    @Value("${server.servlet.context-path}")
    String context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            String requestURI = request.getRequestURI();
            if (("/MinionsDD/auth/process_login").equals(requestURI)) {
                String recaptcha = request.getParameter("g-recaptcha-response");
                RecaptchaResponse recaptchaResponse = recaptchaService.validateToken(recaptcha);
                if (!recaptchaResponse.success()) {
                    log.error("Invalid reCAPTCHA token");
                    response.sendRedirect("login?recaptchaError");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
