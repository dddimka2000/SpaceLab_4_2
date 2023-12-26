package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

@Service
@Log4j2
public class TranslationService {
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    public Map<String, Map<String, String>> getAllTranslations(String language) {
        Locale currentLocale = Locale.getDefault();
        String[] languages;
        if (language != null) {
            languages = new String[]{language};
        } else {
            languages = new String[]{"eng", "ru", "ukr"};
        }

        Map<String, Map<String, String>> translations = new HashMap<>();

        for (String lang : languages) {
            translations.put(lang, loadTranslationsForLanguage(lang, currentLocale));
        }
        return translations;
    }
    public Map<String, String> loadTranslationsForLanguage(String language, Locale locale) {
        Map<String, String> translations = new HashMap<>();
        try {
            log.info("classpath:translation_" + language + ".properties");
            Resource resource = resourceLoader.getResource("classpath:translation_" + language + ".properties");

            try (InputStream inputStream = resource.getInputStream();
                 InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

                Properties properties = new Properties();
                properties.load(reader);

                for (String key : properties.stringPropertyNames()) {
                    String value = properties.getProperty(key);
                    translations.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return translations;
    }

}
