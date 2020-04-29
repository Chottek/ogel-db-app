package pl.fox.ogel_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.fox.ogel_db.model.ProductionDataEntity;
import pl.fox.ogel_db.service.ProductionService;

import java.util.List;

@RestController
@RequestMapping("/api/production")
public class ProductionController {

    private ProductionService service;

    @Autowired
    public ProductionController(ProductionService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductionDataEntity> findByName(@RequestParam(value = "date") String date) {
        return service.getCountedValuesOf(date);
    }


}
