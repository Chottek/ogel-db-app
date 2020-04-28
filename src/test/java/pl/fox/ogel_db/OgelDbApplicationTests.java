package pl.fox.ogel_db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.fox.ogel_db.model.ProductionData;
import pl.fox.ogel_db.service.ProductionService;

import java.util.List;

@SpringBootTest
class OgelDbApplicationTests {


    @Autowired
    private ProductionService service;


    @Test
    void namesOfMachinesAreInOrder() {    // assertion that names gotten from database are in order
        List<String> names = service.getNames();

        assert(names.get(0).equals("4x2 brick mould"));
        assert(names.get(1).equals("2x2 brick mould"));
        assert(names.get(2).equals("3x2 brick mould"));
    }

    @Test
    void givenDateEqualsDateFromDB(){   // assertion that value of date param is equal in all ProductionData objects
        String date = "2018-01-01";
        List<ProductionData> data = service.getTotalValueOf(date);

        for (ProductionData pd: data)
            assert(pd.getDate().equals(date));
    }

}
