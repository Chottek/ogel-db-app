package pl.fox.ogel_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.fox.ogel_db.model.HourlyDataEntity;
import pl.fox.ogel_db.model.ProductionDataEntity;
import pl.fox.ogel_db.service.ProductionService;

import java.util.List;

@RestController
@RequestMapping("/api/production")   // api will send data on '/api/production' mapping
public class ProductionController {

    private ProductionService service;

    @Autowired
    public ProductionController(ProductionService service) {
        this.service = service;
    }

    @GetMapping  // default mapping for getting counted values
    public List<ProductionDataEntity> findByName(@RequestParam(value = "date") String date) {
        return service.getCountedValuesOf(date);
    }

    @GetMapping("/dates")   // '/api/production/dates' <- gets dates distinct
    public List<String> getDates(){
        return service.getDates();
    }

    @GetMapping("/hourly")   // 'api/production/hourly?date={date}' <- gets hourly production based on date parameter
    public List<HourlyDataEntity> getHourlyNetValues(@RequestParam(value = "date") String date){
        return service.countHourlyNetProduction(date);
    }

}
