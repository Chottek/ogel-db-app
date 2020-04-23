package pl.fox.ogel_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fox.ogel_db.model.Runtime;

@Repository
public interface RuntimeRepository extends JpaRepository<Runtime, Integer> {

   /* JpaRepository provides functionalities of Java Persistence Api for searching the database
     like findAll, findAllById, findAllBy*VALUE* */

}
