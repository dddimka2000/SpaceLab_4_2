package org.example.controller;

import org.example.repository.ImageForObjectsRepository;
import org.example.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TranslationControllerTest {

    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private TranslationController translationController;
    @Mock
    private TranslationService translationService;

    @Test
    void getAllTranslations() {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource("classpath:translation_eng.properties");
        lenient().when(resourceLoader.getResource("classpath:translation_eng.properties")).thenReturn(resource);
        Locale.setDefault(Locale.ENGLISH);
        Map<String, Map<String, String>> result = translationController.getAllTranslations("eng");
        assertEquals(0, result.size());
    }
    @Test
    void getAllTranslationsLanguageIsNull() {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource("classpath:translation_eng.properties");
        lenient().when(resourceLoader.getResource("classpath:translation_eng.properties")).thenReturn(resource);
        Locale.setDefault(Locale.ENGLISH);
        Map<String, Map<String, String>> result = translationController.getAllTranslations(null);
        assertEquals(0, result.size());
    }
}