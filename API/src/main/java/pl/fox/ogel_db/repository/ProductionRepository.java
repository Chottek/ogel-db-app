package pl.fox.ogel_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fox.ogel_db.model.ProductionEntity;

import java.util.List;

@Repository
public interface ProductionRepository extends JpaRepository<ProductionEntity, Long> {

    List<ProductionEntity> getByMachineNameAndVariableName(String machineName, String variableName);

    List<ProductionEntity> getByMachineName(String machineName);

}
