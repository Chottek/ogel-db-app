package pl.fox.ogel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.fox.ogel.model.RuntimeDataEntity;
import pl.fox.ogel.service.RuntimeService;

import java.util.List;

@RestController
@RequestMapping("/api/runtime")
public class RuntimeController {

    private RuntimeService service;

    @Autowired
    public RuntimeController(RuntimeService service) {
        this.service = service;
    }

    @GetMapping()
    public List<RuntimeDataEntity> getCountedDownTime(@RequestParam(name = "date") String date) {
        return service.getDownTime(date);
    }
}
