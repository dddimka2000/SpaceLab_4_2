package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.dto.LanguageRequest;
import org.example.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/api")
@Log4j2
public class TranslationController {
    final
    TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }


    @GetMapping("/translations")
    @ResponseBody
    public Map<String, Map<String, String>> getAllTranslations(@RequestParam(name = "language", required = false) String language) {
        return translationService.getAllTranslations(language);
    }


}