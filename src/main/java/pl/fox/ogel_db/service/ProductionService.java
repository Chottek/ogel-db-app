package pl.fox.ogel_db.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel_db.model.HourlyDataEntity;
import pl.fox.ogel_db.model.ProductionDataEntity;
import pl.fox.ogel_db.model.ProductionEntity;
import pl.fox.ogel_db.repository.ProductionRepository;
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
    private static final String TIMEFORMAT = "HH:mm";
    private static final String TIMEZONE = "UTC";

    @Autowired
    public ProductionService(ProductionRepository repository) {
        this.repository = repository;
    }

    public List<ProductionEntity> findAll() {
        return repository.findAll();
    }

    public List<String> getDates() {
        List<String> dates = new ArrayList<>();
        for (ProductionEntity p : findAll()) {
            if (!dates.contains(getStringDate(p.getDatetimeFrom())))
                dates.add(getStringDate(p.getDatetimeFrom()));
        }
        LOG.info("Got list of {} dates -> {} - {}", dates.size(), dates.get(0), dates.get(dates.size() - 1));
        return dates;
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

    private String getStringDate(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));

        return sdf.format(date.getTime());
    }

    private String getStringTime(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));

        return sdf.format(date.getTime());
    }

    private boolean hourEqualsTimeStampHour(int n, Timestamp timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat(TIMEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        return getStringFromHour(n).equals(sdf.format(timestamp.getTime()).split(":")[0]);
    }

    private String getStringFromHour(int n){
        if(n < 10) return "0"+n;
        else return String.valueOf(n);
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


    public List<HourlyDataEntity> countHourlyNetProduction(String date) {
        List<HourlyDataEntity> data = new ArrayList<>();
        int productionValue;
        int scrapValue;

        for (String name : getNames()) {
            for(int i = 0; i < 24; i++){
                productionValue = 0;
                scrapValue = 0;

                for (ProductionEntity p : repository.getByMachineName(name)) {
                    if(dateEqualsTimeStamp(date, p.getDatetimeFrom()) && hourEqualsTimeStampHour(i, p.getDatetimeFrom())){
                        if(p.getVariableName().equals(PRODUCTION_NAME)){
                            productionValue += p.getValue();
                        }

                        if(p.getVariableName().equals(SCRAP_NAME)){
                            scrapValue += p.getValue();
                        }
                    }
                }

                data.add(new HourlyDataEntity(name, i, productionValue - scrapValue));
            }
        }
        LOG.info("Got List of hourly values in count of {}", data.size());
        return data;
    }

    public List<ProductionEntity> getByMachineName(String name, String variable) {
        return repository.getByMachineNameAndVariableName(name, variable);
    }

    public List<ProductionDataEntity> getCountedValuesOf(String date) {
        List<ProductionDataEntity> data = new ArrayList<>();
        LOG.info("Got parameter of date: {}", date);
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

    public int countValue(String machineName, String variable, String date) { // count values
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

    private int parseDateToInt(String date){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
            sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
            var x = sdf.parse(sdf.format(date));

            return (int) x.getTime()/1000;

        }catch(ParseException pe){
            LOG.error("Could not parse date from String!");
        }
        return 0;
    }

    //TODO: Probably better solution is to use predicates to pre-filter results at database side before fetching

}
