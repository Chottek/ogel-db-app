package pl.fox.ogel_db.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor // (Lombok) -> Provides a constructor with all arguments (id, machine_name, datetime and isRunning)
@NoArgsConstructor // Provides an empty default constructor with no arguments
@Getter // Provides getters from all variables
@Setter // Provides setters to all variables
@Entity // Tells that this class is a table entity
@EqualsAndHashCode
@Table(name = "Runtime")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Runtime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String machine_name;
    private Date datetime;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean isrunning;

}
