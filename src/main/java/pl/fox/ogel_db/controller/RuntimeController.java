package pl.fox.ogel_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.fox.ogel_db.model.OeeDataEntity;
import pl.fox.ogel_db.model.RuntimeDataEntity;
import pl.fox.ogel_db.service.RuntimeService;

import java.util.List;

@RestController
@RequestMapping("/api/runtime")
public class RuntimeController {

    private RuntimeService service;

    @Autowired
    public RuntimeController(RuntimeService service) {
        this.service = service;
    }

    @GetMapping
    public List<RuntimeDataEntity> getCountedDownTime(@RequestParam(name = "date") String date) {
        return service.getDownTime(date);
    }

    @GetMapping("/oee")
    public List<OeeDataEntity> getCountedOee(@RequestParam(name = "date") String date){
        return service.getCountedOverallEquipmentEfficiency(date);
    }
}
