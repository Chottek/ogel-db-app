package pl.fox.ogel_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fox.ogel_db.model.Production;
import pl.fox.ogel_db.repository.ProductionRepository;

import java.util.List;

@RestController
@RequestMapping("/api/production")
public class ProductionController {

    private ProductionRepository productionRepository;

    @Autowired   // Injection of ProductionRepository
    public ProductionController(ProductionRepository productionRepository){
        this.productionRepository = productionRepository;
    }

    @GetMapping  // Default GetMapping (/api/production)
    public List<Production> getProductionList(){
        return productionRepository.findAll();
    }

    @GetMapping("/{id}")  // GetMapping for one object, searched by id
    public Production get(@PathVariable("id") int id){
        return productionRepository.getOne(id);
    }

}
