package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ObjectsBuilderController {
    @GetMapping("/object_builder")
    public String ObjectsBuilderShow(){
        return "/objects/object_builders/objectsBuilders";
    }

    @GetMapping("/card_object_builder")
    public String CardObjectsBuilderShow(){
        return "/objects/object_builders/cardObjectBuilder";
    }
    @GetMapping("/editMainInfo")
    public String EditMainInfoObjectsBuilderShow(){
        return "/objects/object_builders/editMainInfo";
    }
}
