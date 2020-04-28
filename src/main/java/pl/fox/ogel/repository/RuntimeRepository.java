package pl.fox.ogel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fox.ogel.model.RuntimeEntity;

import java.util.List;

@Repository
public interface RuntimeRepository extends JpaRepository<RuntimeEntity, Long> {

    List<RuntimeEntity> getByMachineName(String machineName);

}
