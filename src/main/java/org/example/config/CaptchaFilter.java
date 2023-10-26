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
            log.info(response.getHeaderNames());
            String recaptcha = request.getParameter("g-recaptcha-response");

            RecaptchaResponse recaptchaResponse = recaptchaService.validateToken(recaptcha);
            if (!recaptchaResponse.success()) {
                log.info("Invalid reCAPTCHA token");

                // Отправка перенаправления на страницу с ошибкой
                response.sendRedirect("login?recaptchaError"); // Указать URL вашей страницы с ошибкой
                return; // Важно прервать дальнейшее выполнение фильтра
            }
        }
        filterChain.doFilter(request, response);
    }
}
