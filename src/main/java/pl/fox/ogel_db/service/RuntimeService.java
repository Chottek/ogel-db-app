package pl.fox.ogel_db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel_db.model.Production;
import pl.fox.ogel_db.model.Runtime;
import pl.fox.ogel_db.model.RuntimeData;
import pl.fox.ogel_db.repository.RuntimeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RuntimeService {

    private RuntimeRepository runtimeRepository;
    private ProductionService productionService;


    @Autowired
    public RuntimeService(RuntimeRepository runtimeRepository, ProductionService productionService){
        this.runtimeRepository = runtimeRepository;
        this.productionService = productionService;
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

    public List<Runtime> findByNameAndDate(String name, String date){
        return runtimeRepository.findByMachineName(name, date);
    }

    public List<RuntimeData> getUptime(String date){
        List<RuntimeData> data = new ArrayList<>();
       for(String machine_name : productionService.getNames()){
           int uptime = 0;
           for(Production p : productionService.getByVarDate(machine_name, date)){
               if(p.getValue() != 0)
                   uptime += 5;
           }

           data.add(new RuntimeData(machine_name, countUpTime(uptime), countDownTime(uptime),
                   (100 - countDownTimePercentage(uptime)), countDownTimePercentage(uptime) ));
       }

       return data;
    }

    private float countUpTime(float uptime){
        return uptime / 60;
    }

    private float countDownTime(float uptime){
        return  24 - (uptime / 60) ;
    }

    private float countDownTimePercentage(float uptime){
        return (100 * countDownTime(uptime)) / 24;
    }

    //TODO: Make new Object class that contains calculated downTime Percentage of machine
}
