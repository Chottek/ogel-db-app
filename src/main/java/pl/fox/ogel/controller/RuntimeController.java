package pl.fox.ogel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fox.ogel.model.RuntimeDataEntity;
import pl.fox.ogel.service.RuntimeService;

import java.util.List;

@RestController
public class RuntimeController {

    private RuntimeService service;

    @Autowired
    public RuntimeController(RuntimeService service){
        this.service = service;
    }

    @GetMapping
    public List<RuntimeDataEntity> getCountedDownTime(String date){
        return service.getDownTime(date);
    }





}
