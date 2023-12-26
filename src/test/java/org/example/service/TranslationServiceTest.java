package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;
import java.util.Map;

public class TranslationServiceTest {

    @Test
    public void testGetAllTranslationsWithValidLanguage() {
        TranslationService translationService = new TranslationService();
        String validLanguage = "eng"; // Assuming "eng" is a valid language code

        // Act
        Map<String, Map<String, String>> translations = translationService.getAllTranslations(validLanguage);

        // Assert
        assertNotNull(translations);
        assertTrue(translations.containsKey(validLanguage));
        assertFalse(translations.get(validLanguage).isEmpty());
    }

    @Test
    public void testGetAllTranslationsWithNullLanguage() {
        TranslationService translationService = new TranslationService();

        Map<String, Map<String, String>> translations = translationService.getAllTranslations(null);

        assertNotNull(translations);
        assertTrue(translations.containsKey("eng"));
        assertTrue(translations.containsKey("ru"));
        assertTrue(translations.containsKey("ukr"));
        assertFalse(translations.get("eng").isEmpty());
        assertFalse(translations.get("ru").isEmpty());
        assertFalse(translations.get("ukr").isEmpty());
    }

    @Test
    public void testLoadTranslationsForLanguageWithValidLanguage() {
        TranslationService translationService = new TranslationService();
        String language = "eng"; // Assuming "en" is a valid language code

        Map<String, String> translations = translationService.loadTranslationsForLanguage(language, Locale.getDefault());

        assertNotNull(translations);
        assertFalse(translations.isEmpty());
    }

    @Test
    public void testLoadTranslationsForLanguageWithInvalidLanguage() {
        TranslationService translationService = new TranslationService();
        String language = "invalidLang"; // Assuming "invalidLang" is an invalid language code

        Map<String, String> translations = translationService.loadTranslationsForLanguage(language, Locale.getDefault());

        assertNotNull(translations);
        assertTrue(translations.isEmpty());
    }
}
