package pl.fox.ogel_db.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //ignores the specified logical properties in JSON serialization and deserialization, done to avoid exception
public class Production {

    @Id
    @Size(max = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String machine_name;
    private String variable_name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime_from;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime_to;

    @Column(name = "value")
    private Integer value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public String getVariable_name() {
        return variable_name;
    }

    public void setVariable_name(String variable_name) {
        this.variable_name = variable_name;
    }

    public Date getDatetime_from() {
        return datetime_from;
    }

    public void setDatetime_from(Date datetime_from) {
        this.datetime_from = datetime_from;
    }

    public Date getDatetime_to() {
        return datetime_to;
    }

    public void setDatetime_to(Date datetime_to) {
        this.datetime_to = datetime_to;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
