package pl.fox.ogel_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.fox.ogel_db.model.OEEData;
import pl.fox.ogel_db.model.Runtime;
import pl.fox.ogel_db.model.RuntimeData;
import pl.fox.ogel_db.service.RuntimeService;

import java.util.List;

@RestController
@RequestMapping("/api/runtime")
public class RuntimeController {

    private RuntimeService service;

    @Autowired
    public RuntimeController(RuntimeService service){
        this.service = service;
    }


    @GetMapping("/machines/{name}")
    public List<Runtime> getByNameAndDate(@PathVariable("name") String name, @RequestParam("date") String date){
        return service.findByNameAndDate(name, date);
    }

    @GetMapping("/time")
    public List<RuntimeData> getData(@RequestParam("date") String date){
        return service.getUptime(date);
    }

    @GetMapping("/oee")
    public List<OEEData> getOEE(@RequestParam("date") String date){
        return service.countOEE(date);
    }

}
