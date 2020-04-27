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

    public List<ProductionData> getTotalValueOf(String date){     // method to get and count values
        List<ProductionData> data = new ArrayList<>();   // init of arraylist of data

        for(String machine_name : getNames()){     // loop for each machine name in database
            int productionValue = 0;               // production value variable
            int scrapValue = 0;                    // scrap value variable
            double temperatureValue = 0;           // temperature variable

            for(Production p : productionRepository.findByDate(machine_name, "PRODUCTION", date))  // loop for getting production value
                productionValue += p.getValue();

            for(Production p : productionRepository.findByDate(machine_name, "SCRAP", date))  // loop for getting scrap value
                scrapValue += p.getValue();


            double more85 = 0;   // init of variable used to count whether temperature is higher than 85 degrees
            boolean more100 = false;  // init of boolean set if temperature is more than 100 degrees
            double tempInRow = 0;   // init of variable used to count if temperature was higher than 85 degrees for more than 15 minutes
            int flag = 0;   // init of variable that is added when tempInRow variable was higher than 85 degrees for more than 15 minutes
            for(Production p : productionRepository.findByDate(machine_name, "CORE TEMPERATURE", date)){
                temperatureValue += p.getValue();      // add to temperature value on each iteration

                if(p.getValue() > 100)  // set boolean true when value of temperature was higher than 100 degrees even once
                    more100 = true;

                if(p.getValue() <= 85)  // set counter to 0 if temperature in current iteration of loop was lower than 85 degrees
                    tempInRow = 0;

                if(p.getValue() > 85 && p.getValue() <= 100){   // add to variables if temperature is more than 85 and less or equal to 100
                    more85++;
                    tempInRow++;
                }

                if(tempInRow >= 3)  // add to flag if temperature was more than 85 degrees and less or equal to 100 three times in row
                    flag++;

            }

            int warning = 0;  // initialize warning

            if(more85 <= 3 && flag > 0)  // each database row has 5-minute check interval, so 15 minutes is 3 times in a row
                warning = 1;             // if flag value of more85 is less or equal than 3 and flag value != 0, set warning to 1

            if(more100 || (more85 > 3 && flag > 0)) // if temperature has been even once higher than 100 degrees or was more
                warning = 2;                        // than 85 more than three times in a row, set warning to 2

            // add counted values to arraylist using constructor of ProductionData object
            data.add(new ProductionData(machine_name, productionValue, scrapValue,
                    countNetProduction(productionValue, scrapValue), countPercentage(productionValue, scrapValue),
                    (100 - countPercentage(productionValue, scrapValue)),
                    (temperatureValue / productionRepository.findByDate(machine_name, "CORE TEMPERATURE", date).size()),
                    warning, date));
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
