package pl.fox.ogel_db.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fox.ogel_db.model.ProductionEntity;
import pl.fox.ogel_db.model.RuntimeDataEntity;
import pl.fox.ogel_db.repository.RuntimeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuntimeService {

    private static final String PRODUCTION_NAME = "PRODUCTION";

    private RuntimeRepository repository;
    private ProductionService productionService;

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeService.class);
;
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
                if (p.getVariableName().equals(PRODUCTION_NAME) &&
                        productionService.dateEqualsTimeStamp(date, p.getDatetimeFrom()) && p.getValue() == 0) {
                    value += 5;
                }
            }
            data.add(new RuntimeDataEntity((float) (100 * value) / (24 * 60 / 5 + value)));
            LOG.info("Added to RuntimeDataEntity value {} for machine name {}", (float) (100 * value) / (24 * 60 / 5 + value), machineName);
        }
        return data;
    }
}
