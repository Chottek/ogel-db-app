package pl.fox.ogel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel.model.ProductionDataEntity;
import pl.fox.ogel.model.ProductionEntity;
import pl.fox.ogel.repository.ProductionRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class ProductionService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductionService.class);

    private ProductionRepository repository;

    private static final String PRODUCTION_NAME = "PRODUCTION";
    private static final String SCRAP_NAME = "SCRAP";
    private static final String TEMPERATURE_NAME = "CORE TEMPERATURE";
    private static final String DATEFORMAT = "yyyy-MM-dd";
    private static final String TIMEZONE = "UTC";

    @Autowired
    public ProductionService(ProductionRepository repository) {
        this.repository = repository;
    }

    public List<ProductionEntity> findAll() {
        return repository.findAll();
    }

    public List<String> getNames() {
        List<String> data = new ArrayList<>();

        for (ProductionEntity p : findAll()) {
            if (!data.contains(p.getMachineName())) {
                data.add(p.getMachineName());
            }
        }
        return data;
    }

    public boolean dateEqualsTimeStamp(String date, Timestamp timestamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
            sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));

            return sdf.format(sdf.parse(date)).equals(sdf.format(timestamp.getTime()));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return false;
    }

    private List<ProductionEntity> getByMachineName(String name, String variable) {
        return repository.getByMachineNameAndVariableName(name, variable);
    }

    public List<ProductionDataEntity> getCountedValuesOf(String date) {
        List<ProductionDataEntity> data = new ArrayList<>();
        for (String machineName : getNames()) {

            int production = countValue(machineName, PRODUCTION_NAME, date);
            int scrap = countValue(machineName, SCRAP_NAME, date);
            int warning = countWarning(machineName, date);

            data.add(new ProductionDataEntity(machineName, (production - scrap),
                    countScrapPercentage(scrap, production),
                    (100 - countScrapPercentage(scrap, production)), warning));

            LOG.info("Added to list: ProductionDataEntity with values: {}, {}, {}, {}, {}", machineName, (production - scrap),
                    countScrapPercentage(scrap, production),
                    (100 - countScrapPercentage(scrap, production)), warning);

        }
        return data;
    }

    private float countScrapPercentage(int scrap, int gross) {
        return (float) (100 * scrap) / (scrap + gross);
    }

    private int countValue(String machineName, String variable, String date) { // count values
        int value = 0;
        for (ProductionEntity p : getByMachineName(machineName, variable)) {
            if (dateEqualsTimeStamp(date, p.getDatetimeFrom())) {
                value += p.getValue();
            }
        }
        return value;
    }


    private int countWarning(String machine_name, String date) {  // method to count temperature, returns 0 - good, 1 - warning or 2 - fatal
        int highest = 0;
        int rowCount = 0;
        boolean row = false;
        boolean moreRow = false;

        for (ProductionEntity p : repository.getByMachineNameAndVariableName(machine_name, TEMPERATURE_NAME)) {
            if (!dateEqualsTimeStamp(date, p.getDatetimeFrom()))
                continue;

            if (p.getValue() > highest) {
                highest = p.getValue();
            }

            if (p.getValue() > 100) {
                LOG.info("Machine name: {}, highest Temperature: {}", machine_name, p.getValue());
                return 2;
            }

            if (p.getValue() > 85 && p.getValue() <= 100) {
                rowCount++;
            }

            if (p.getValue() <= 85) {
                rowCount = 0;
            }

            if (rowCount == 3) {
                row = true;
            }

            if (rowCount > 3) {
                moreRow = true;
            }
        }

        LOG.info("Machine name: {}, Counted highest temperature: {}", machine_name, highest);
        //return value based on counted values
        if (highest <= 85) {
            return 0;
        } else if (row && !moreRow) {
            return 1;
        } else if (highest > 100 || moreRow) {
            return 2;
        }

        return 0;
    }
}
