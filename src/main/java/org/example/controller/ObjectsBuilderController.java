package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.dto.ObjectBuilderDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/objects/byBuilder")
@Log4j2
public class ObjectsBuilderController {
    @GetMapping
    public String ObjectsBuilderShow() {
        return "/objects/object_builders/objectsBuilders";
    }

    @GetMapping("/create")
    public String ObjectsBuilderCreate() {
        return "/objects/object_builders/newObjectBuilder";
    }

    @PostMapping("/create")
    public String ObjectsBuilderCreatePost(@ModelAttribute ObjectBuilderDto objectBuilderDto) {
        log.info(objectBuilderDto.getPrices().get().getOriginalFilename());
        log.info(objectBuilderDto);
        return "/objects/object_builders/newObjectBuilder";
    }

    @GetMapping("/card_object_builder")
    public String CardObjectsBuilderShow() {
        return "/objects/object_builders/cardObjectBuilder";
    }

    @GetMapping("/editMainInfo")
    public String EditMainInfoObjectsBuilderShow() {
        return "/objects/object_builders/editMainInfo";
    }
}
