package pl.fox.ogel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel.model.ProductionEntity;
import pl.fox.ogel.model.RuntimeDataEntity;
import pl.fox.ogel.model.RuntimeEntity;
import pl.fox.ogel.repository.RuntimeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuntimeService {

    private RuntimeRepository repository;
    private ProductionService productionService;

    @Autowired
    public RuntimeService(RuntimeRepository repository, ProductionService productionService) {
        this.repository = repository;
        this.productionService = productionService;
    }

    public List<RuntimeDataEntity> getDownTime(String date) {
        List<RuntimeDataEntity> data = new ArrayList<>();
        int value = 0;
        for (String machineName : productionService.getNames()) {
            for (ProductionEntity p : productionService.findAll()) {
                if (p.getVariableName().equals("PRODUCTION") &&
                        productionService.dateEqualsTimeStamp(date, p.getDatetimeFrom()) && p.getValue() == 0)
                    value += 5;
            }
            data.add(new RuntimeDataEntity((float) (100 * value) / ( 24 * 60 / 5 + value)));
        }
        return data;
    }

    // up + down = 100% (24h)
    // down = x


}
