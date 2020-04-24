package pl.fox.ogel_db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel_db.model.Production;
import pl.fox.ogel_db.model.Runtime;
import pl.fox.ogel_db.repository.ProductionRepository;
import pl.fox.ogel_db.repository.RuntimeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RuntimeService {

    private RuntimeRepository runtimeRepository;

    @Autowired
    public RuntimeService(RuntimeRepository runtimeRepository){
        this.runtimeRepository = runtimeRepository;
    }

    public List<Runtime> findAll(){
        return runtimeRepository.findAll();
    }

    public Optional<Runtime> findById(int id) {
        return runtimeRepository.findById(id);
    }

    public List<Runtime> findByName(String machine_name){
        return runtimeRepository.findByMachine_name(machine_name);
    }

}
