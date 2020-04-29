package pl.fox.ogel_db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.fox.ogel_db.model.HourlyDataEntity;
import pl.fox.ogel_db.service.ProductionService;

import java.util.List;

@SpringBootTest
class OgelApplicationTests {

    @Autowired
    private ProductionService service;

    @Test
    public void machineNamesAreInOrder(){
        List<String> names = service.getNames();

        assert(names.get(0).equals("4x2 brick mould"));
        assert(names.get(1).equals("2x2 brick mould"));
        assert(names.get(2).equals("3x2 brick mould"));

        // assertion that names got from database are in order
    }

    @Test
    public void thereAre72ValuesOfHourlyDataFromDate(){
        List<HourlyDataEntity> data;
        for(String date : service.getDates()){
            data = service.countHourlyNetProduction(date);
            assert(data.size() == 72);
        }
        //assertion that method gets 72 objects of HourlyDataEntity for each date
    }

    @Test
    public void thereAre7DatesInDatabase(){
        List<String> dates = service.getDates();

        assert(dates.size() == 7);
        //assertion that method is getting dates correctly
    }
}
