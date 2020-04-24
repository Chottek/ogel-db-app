package pl.fox.ogel_db.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fox.ogel_db.model.Production;
import pl.fox.ogel_db.repository.ProductionRepository;
import pl.fox.ogel_db.service.ProductionService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/list")
    public List<String> getDistinct(){
        List<String> names = new ArrayList<>();
        for (Production p : service.findDistinctByName())
            if(!names.contains(p.getMachine_name()))
                 names.add(p.getMachine_name());

        return names;
    }


//    @GetMapping("/machines")
//    public List<Production> getByNameAndValue(@RequestParam(value = "value") String value, @RequestParam(value = "name") String name){
//        return service.findByVariable(value, name);
//    }

    @RequestMapping(value="/machines", method = RequestMethod.GET)
    public List<Production> getByName(@RequestParam(value="name") String name) {
        return service.findByName(name);
    }


    @PostConstruct  // Just for checking purposes
    public void checkRepository(){
        if(!service.findAll().isEmpty())
            System.out.println("PRODUCT REPOSITORY IS FILLED WITH DATA");
        else
            System.err.println("PRODUCT REPOSITORY IS EMPTY!");
    }

}
