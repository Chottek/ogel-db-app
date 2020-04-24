package pl.fox.ogel_db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel_db.model.Production;
import pl.fox.ogel_db.repository.ProductionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionService {

    private ProductionRepository productionRepository;

    @Autowired
    public ProductionService(ProductionRepository productionRepository){
        this.productionRepository = productionRepository;
    }

    public List<Production> findAll(){
        return productionRepository.findAll();
    }

    public Optional<Production> findById(int id) {
        return productionRepository.findById(id);
    }

    public List<Production> findByName(String machine_name){
        return productionRepository.findByMachine_name(machine_name);
    }

    public List<Production> findAllWithOrder(){
        return productionRepository.findAllOrderByName();
    }

    public List<Production> findByVariable(String variable, String machine_name){
        return productionRepository.findByVariable(variable, machine_name);
    }

    public List<Production> findDistinctByName(){
        return productionRepository.findDistinctByName();
    }

}
