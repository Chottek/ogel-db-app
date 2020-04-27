package pl.fox.ogel_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.fox.ogel_db.model.Production;
import pl.fox.ogel_db.model.ProductionData;
import pl.fox.ogel_db.service.ProductionService;

import java.util.List;

@RestController
@RequestMapping("/api/production")
public class ProductionController {

    private ProductionService service;

    @Autowired   // Injection of ProductionRepository
    public ProductionController(ProductionService service){
        this.service = service;
    }

    @GetMapping  // Default GetMapping (/api/production)
    public List<Production> getProductionList(){
        return service.findAllWithOrder();
    }

//    @GetMapping("/{id}")  // GetMapping for one object, searched by id
//    public ResponseEntity<Production> get(@PathVariable("id") int id){
//        Optional<Production> production = service.findById(id);
//        if (production.isPresent())
//            return new ResponseEntity(production, HttpStatus.OK);
//
//        return ResponseEntity.notFound().build();
//    }

    @GetMapping("/machines")
    public List<ProductionData> getTotalValueOf(@RequestParam(value="date") String date){
        return service.getTotalValueOf(date);
    }

}
