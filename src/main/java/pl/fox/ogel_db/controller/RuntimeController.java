package pl.fox.ogel_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fox.ogel_db.model.Runtime;
import pl.fox.ogel_db.repository.RuntimeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/runtime")
public class RuntimeController {

    private RuntimeRepository runtimeRepository;

    @Autowired
    public RuntimeController(RuntimeRepository runtimeRepository){
        this.runtimeRepository = runtimeRepository;
    }

    @GetMapping
    public List<Runtime> getRuntimeList(){
        return runtimeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Runtime get(@PathVariable("id") int id){
        return runtimeRepository.getOne(id);
    }

}
