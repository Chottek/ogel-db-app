package pl.fox.ogel_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.fox.ogel_db.model.Production;
import pl.fox.ogel_db.service.ProductionService;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.ArrayList;
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

    @GetMapping("/list")
    public List<Production> getDistinct(){
        List<String> temp = new ArrayList<>();
        List<Production> distinct = new ArrayList<>();
        for(Production p : service.findAll())
            if(!temp.contains(p.getMachine_name())){
                temp.add(p.getMachine_name());
                distinct.add(p);
            }

        return distinct;
    }




    @GetMapping("/machines")
    public List<Production> getByName(@RequestParam(value="name") String name) {
        return service.findByName(name);
    }

//    @GetMapping("/machines/{name}/{variable}")
//    public List<Production> getByNameAndVar(@PathVariable(value="variable") String variable, @PathVariable(value="name") String name){
//        return service.findByVariable(variable, name);
//    }

    @GetMapping("/machines/{name}/{variable}")
    public List<Production> getNameVarDate(@PathVariable(value="name") String name, @PathVariable(value="variable") String variable, @RequestParam(value="date") String date) throws ParseException {
       System.out.println(date);
        return service.gfr(name, variable, date);
    }


    @PostConstruct  // Just for checking purposes
    public void checkRepository(){
        if(!service.findAll().isEmpty())
            System.out.println("PRODUCT REPOSITORY IS FILLED WITH DATA");
        else
            System.err.println("PRODUCT REPOSITORY IS EMPTY!");
    }

}
