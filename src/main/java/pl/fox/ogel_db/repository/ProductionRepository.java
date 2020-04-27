package pl.fox.ogel_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.fox.ogel_db.model.Production;

import java.util.List;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Integer> {

    @Query("SELECT t from Production t WHERE t.machine_name=?1 ORDER BY t.machine_name ASC")
    List<Production> findByMachine_name(String machine);

    @Query("SELECT t from Production t ORDER BY t.machine_name ASC")
    List<Production> findAllOrderByName();

    @Query("SELECT t from Production t WHERE t.machine_name=?1 AND t.variable_name=?2 AND SUBSTRING(t.datetime_from, 1, 10)=?3")
    List<Production> findByDate(String name, String variable, String datetime);


}
