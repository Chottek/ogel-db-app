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

    @Autowired   // injection of ProductionRepository to get data from database
    public ProductionService(ProductionRepository productionRepository){
        this.productionRepository = productionRepository;
    }

    public List<Production> findAll(){    // method that gets all values from database
        return productionRepository.findAll();
    }


    public List<Production> getByVarDate(String name, String date){     // method that gets list of Production objects from database
        return productionRepository.findByDate(name, "PRODUCTION", date);    // (used in RuntimeService to get machines running time)
    }


    public List<String> getNames(){   // method to get machine names
        List<String> distinct = new ArrayList<>();  // init of list of Strings

        for(Production p : findAll())    // loop to iterate on each object in list returned by findAll() method
            if(!distinct.contains(p.getMachine_name()))  // check if list already contains name
                distinct.add(p.getMachine_name());   // if not, add name gotten in current iteration of loop

        return distinct;    // return list of Strings
    }

    private int countValue(String machine_name, String variable, String date){
        int value = 0;
        for(Production p : productionRepository.findByDate(machine_name, variable, date))
            value += p.getValue();

        return value;
    }

    private int countWarning(String machine_name, String date){
        int value = 0;
        int highest = 0;
        int rowCount = 0;
        boolean row = false;
        boolean moreRow = false;

        for(Production p : productionRepository.findByDate(machine_name, "CORE TEMPERATURE", date)) {
            if(p.getValue() > highest)
                highest = p.getValue();

            if(highest > 85 && highest <= 100)
                rowCount++;

            if(highest <= 85)
                rowCount = 0;

            if(rowCount == 3)
                row = true;

            if(rowCount > 3)
                moreRow = true;
        }

        if(highest <= 85)
            return 0;
        else if(row && !moreRow)
            return 1;
        else if(highest > 100 || moreRow)
            return 2;

        return value;
    }

    public List<ProductionData> getTotalValueOf(String date){     // method to get and count values
        List<ProductionData> data = new ArrayList<>();   // init of arraylist of data

        for(String machine_name : getNames()){     // loop for each machine name in database
            double temperatureValue = 0;           // temperature variable


            int prodValue = countValue(machine_name, "PRODUCTION", date);
            int scrapValue = countValue(machine_name, "SCRAP", date);
            int warningValue = countWarning(machine_name, date);


            // add counted values to arraylist using constructor of ProductionData object
            data.add(new ProductionData(machine_name, prodValue, scrapValue,
                    countNetProduction(prodValue, scrapValue), countPercentage(prodValue, scrapValue),
                    (100 - countPercentage(prodValue, scrapValue)), warningValue, date));
        }

        return data;    // return arraylist
    }

    private float countPercentage(int production, int scrap){    // count gross percentage
        return (float) 100 * (production - scrap) / (production + scrap);
    }

    private int countNetProduction(int production, int scrap){   // count net production
        return production - (2 * scrap);
    }


    //TODO: Get data to generate a table showing the net production for each hour.

}
