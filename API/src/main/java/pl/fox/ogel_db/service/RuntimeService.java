package pl.fox.ogel_db.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel_db.model.OeeDataEntity;
import pl.fox.ogel_db.model.ProductionEntity;
import pl.fox.ogel_db.model.RuntimeDataEntity;
import pl.fox.ogel_db.repository.RuntimeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuntimeService {

    private static final String PRODUCTION_NAME = "PRODUCTION";
    private static final String SCRAP_NAME = "SCRAP";
    private static final Integer UPTIME_NORM = 16; // availability norm: 16 hours
    private static final Integer H_PRODUCTION_NORM = 30000; // hourly production norm: 30'000 bricks

    private RuntimeRepository repository;
    private ProductionService productionService;

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeService.class);


    @Autowired
    public RuntimeService(RuntimeRepository repository, ProductionService productionService) {
        this.repository = repository;
        this.productionService = productionService;
    }

    public List<RuntimeDataEntity> getDownTime(String date) {    // count downtime percentage based on uptime
        List<RuntimeDataEntity> data = new ArrayList<>();
        for (String machineName : productionService.getNames()) {
            int uptime = 0;
            for (ProductionEntity p : productionService.getByMachineName(machineName, PRODUCTION_NAME)) {
                if (productionService.dateEqualsTimeStamp(date, p.getDatetimeFrom()) && p.getValue() != 0)
                    uptime += 5;
            }
            data.add(new RuntimeDataEntity(countDownTimePercentage(uptime)));
            LOG.info("Added to RuntimeDataEntity value {} for machine name {}", countDownTimePercentage(uptime), machineName);
        }
        return data;
    }

    private float countDownTimePercentage(float uptime) {
        return 100 - ((uptime / 60) * 100 / 24);
    }

    public List<OeeDataEntity> getCountedOverallEquipmentEfficiency(String date) {
        List<OeeDataEntity> data = new ArrayList<>();
        for (String name : productionService.getNames()) {
            data.add(new OeeDataEntity(countPerformanceOrQuality(name, date, false),
                    countAvailability(name, date),
                    countPerformanceOrQuality(name, date, true)));
            LOG.info("Added new OeeDataEntity to list with values: {}, {}, {}",
                    countPerformanceOrQuality(name, date, false),
                    countAvailability(name, date), countPerformanceOrQuality(name, date, true));
        }
        return data;
    }

        // count performance if boolean is false, quality if boolean is true
    private float countPerformanceOrQuality(String machineName, String date, boolean quality) {
        int prodValue = productionService.countValue(machineName, PRODUCTION_NAME, date);
        int scrapValue = productionService.countValue(machineName, SCRAP_NAME, date);

        if (quality){ // return counted quality if boolean is true
            return (float) (prodValue - 2 * scrapValue) / (prodValue - scrapValue) * 100;
        }

        return (float) (prodValue - scrapValue) / (H_PRODUCTION_NORM * 24) * 100;  // else return counted performance based on Hourlyy Production Norm
    }

    private float countAvailability(String name, String date) {
        int value = 0;
        for (ProductionEntity p : productionService.getByMachineName(name, PRODUCTION_NAME)) {
            if (productionService.dateEqualsTimeStamp(date, p.getDatetimeFrom()) && p.getValue() != 0) {
                value++;
            }
        }
        return (float) value / (float) (UPTIME_NORM * 60 / 5) * 100;   // return value based on UPTIME_NORM
    }
}
