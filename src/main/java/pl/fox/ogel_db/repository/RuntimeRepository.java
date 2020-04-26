package pl.fox.ogel_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.fox.ogel_db.model.Runtime;

import java.util.List;

@Repository
public interface RuntimeRepository extends JpaRepository<Runtime, Integer> {

   /* JpaRepository provides functionalities of Java Persistence Api for searching the database
     like findAll, findAllById, findAllBy*VALUE* */

    @Query("SELECT t from Runtime t WHERE t.machine_name= ?1 ORDER BY t.machine_name ASC")
    List<Runtime> findByMachine_name(String machine);

    @Query("SELECT t from Runtime t WHERE t.machine_name=?1 AND SUBSTRING(t.datetime, 1, 10)=?2")
    List<Runtime> findByMachineName(String machine, String date);

}
