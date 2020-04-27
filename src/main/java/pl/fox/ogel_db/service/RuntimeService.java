package pl.fox.ogel_db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel_db.model.Production;
import pl.fox.ogel_db.model.Runtime;
import pl.fox.ogel_db.model.RuntimeData;
import pl.fox.ogel_db.repository.RuntimeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuntimeService {

    private RuntimeRepository runtimeRepository;
    private ProductionService productionService;


    @Autowired
    public RuntimeService(RuntimeRepository runtimeRepository, ProductionService productionService){
        this.runtimeRepository = runtimeRepository;
        this.productionService = productionService;
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
           data.add(new RuntimeData(machine_name, countTime(uptime, false), countTime(uptime, true),
                   (100 - countDownTimePercentage(uptime)), countDownTimePercentage(uptime)));
       }
       return data;
    }

    private float countTime(float uptime, boolean down){
        if(!down) return uptime / 60;
        return 24 - (uptime / 60);
    }

    private float countDownTimePercentage(float uptime){
        return (100 * countTime(uptime, true)) / 24;
    }
}
