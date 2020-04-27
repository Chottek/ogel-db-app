package pl.fox.ogel_db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel_db.model.Production;
import pl.fox.ogel_db.model.ProductionData;
import pl.fox.ogel_db.repository.ProductionRepository;

import java.util.*;

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

    public List<Production> findAllWithOrder(){
        return productionRepository.findAllOrderByName();
    }

    public List<Production> getByVarDate(String name, String date){
        return productionRepository.findByDate(name, "PRODUCTION", date);
    }


    public List<String> getNames(){
        List<String> temp = new ArrayList<>();
        List<String> distinct = new ArrayList<>();

        for(Production p : findAll())
            if(!temp.contains(p.getMachine_name())){
                temp.add(p.getMachine_name());
                distinct.add(p.getMachine_name());
            }

        return distinct;
    }

    public List<ProductionData> getTotalValueOf(String date){
        List<ProductionData> data = new ArrayList<>();

        for(String machine_name : getNames()){
            int productionValue = 0;
            int scrapValue = 0;
            double temperatureValue = 0;
            int counter = 0;

            for(Production p : productionRepository.findByDate(machine_name, "PRODUCTION", date))
                productionValue += p.getValue();

            for(Production p : productionRepository.findByDate(machine_name, "SCRAP", date))
                scrapValue += p.getValue();

            for(Production p : productionRepository.findByDate(machine_name, "CORE TEMPERATURE", date)){
                temperatureValue += p.getValue();
                counter++;
            }

            data.add(new ProductionData(machine_name, productionValue, scrapValue,
                    countNetProduction(productionValue, scrapValue), countPercentage(productionValue, scrapValue),
                    (100 - countPercentage(productionValue, scrapValue)), (temperatureValue / counter), date));
        }

        return data;
    }

    private float countPercentage(int production, int scrap){
        return (float) 100 * (production - scrap) / (production + scrap);
    }

    private int countNetProduction(int production, int scrap){
        return production - (2 * scrap);
    }

}
