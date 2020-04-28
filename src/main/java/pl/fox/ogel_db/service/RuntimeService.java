package pl.fox.ogel_db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel_db.model.OEEData;
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

    private List<Production> values;


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
           for(Production p : productionService.getByProductionDate(machine_name, date)){
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

    private float countPerformanceOrQuality(String machine_name, String date, boolean quality){
        int prodValue = 0;
        int scrapValue = 0;
        values = productionService.findByDate(machine_name, "PRODUCTION", date);

        for(Production p : values)
            prodValue += p.getValue();

        values = productionService.findByDate(machine_name, "SCRAP", date);

       for(Production p : values)
            scrapValue += p.getValue();

       if(quality)
           return (float) (prodValue - 2 * scrapValue) / (prodValue - scrapValue) * 100;

        return (float) (prodValue - scrapValue) / (30000 * 24) * 100;
    }

    private float countAvailability(String machine_name, String date){
        int uptime = 0;
        values = productionService.getByProductionDate(machine_name, date);
        for(Production p : values)
            if(p.getValue() != 0)
                uptime++;

            int norm = 16 * 60 / 5;

            return (float) uptime / (float) norm * 100;
    }

    public List<OEEData> countOEE(String date){
        List<OEEData> data = new ArrayList<>();
        for(String name : productionService.getNames())
            data.add(new OEEData(name, countPerformanceOrQuality(name, date, false), countAvailability(name, date), countPerformanceOrQuality(name, date, true)));

        return data;
    }
}
