package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfig {
    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/"); // Путь к шаблонам внутри JAR-файла
        templateResolver.setSuffix(".html"); // Расширение файлов шаблонов
        templateResolver.setTemplateMode("HTML"); // Режим шаблонизации (HTML, XML и т.д.)
        templateResolver.setCharacterEncoding("UTF-8"); // Кодировка шаблонов
        return templateResolver;
    }
}
