package pl.fox.ogel_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fox.ogel_db.model.Production;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Integer> {

}
