package pl.fox.ogel_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fox.ogel_db.model.Production;
import pl.fox.ogel_db.model.Runtime;
import pl.fox.ogel_db.repository.RuntimeRepository;
import pl.fox.ogel_db.service.RuntimeService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runtime")
public class RuntimeController {

    private RuntimeService service;

    @Autowired
    public RuntimeController(RuntimeService service){
        this.service = service;
    }

    @GetMapping
    public List<Runtime> getRuntimeList(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Runtime> get(@PathVariable("id") int id){
        Optional<Runtime> runtime = service.findById(id);
        if (runtime.isPresent())
            return new ResponseEntity(runtime, HttpStatus.OK);

      return ResponseEntity.notFound().build();
    }

//    @RequestMapping(value="/machines", method = RequestMethod.GET)
//    public List<Runtime> getByName(@RequestParam(value="name") String name) {
//        return service.findByName(name);
//    }


    @GetMapping("/machines/{name}")
    public List<Runtime> getByNameAndDate(@PathVariable("name") String name, @RequestParam("date") String date){
        return service.findByNameAndDate(name, date);
    }


    @PostConstruct   // Just for checking purposes
    public void checkRepository(){
        if(!service.findAll().isEmpty())
            System.out.println("RUNTIME REPOSITORY IS FILLED WITH DATA");
        else
            System.err.println("RUNTIME REPOSITORY IS EMPTY!");
    }
}
