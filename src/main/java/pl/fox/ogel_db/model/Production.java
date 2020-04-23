package pl.fox.ogel_db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Production")
public class Production {

    @Id
    @Size(max = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String machine_name;
    private String variable_name;
    @Temporal(TemporalType.DATE)
    private Date datetime_from;
    @Temporal(TemporalType.DATE)
    private Date datetime_to;

    @Column(name = "value")
    private Integer value;


}
