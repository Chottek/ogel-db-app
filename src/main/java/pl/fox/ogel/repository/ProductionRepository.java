package pl.fox.ogel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fox.ogel.model.ProductionEntity;

import java.util.List;

@Repository
public interface ProductionRepository extends JpaRepository<ProductionEntity, Long> {

    List<ProductionEntity> getByMachineNameAndVariableName(String machineName, String variableName);

}
