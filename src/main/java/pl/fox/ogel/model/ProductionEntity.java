package pl.fox.ogel.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "production")
public class ProductionEntity {

    private Integer id;
    private String machineName;
    private String variableName;
    private Timestamp datetimeFrom;
    private Timestamp datetimeTo;
    private int value;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "machine_name")
    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    @Basic
    @Column(name = "variable_name")
    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Basic
    @Column(name = "datetime_from")
    public Timestamp getDatetimeFrom() {
        return datetimeFrom;
    }

    public void setDatetimeFrom(Timestamp datetimeFrom) {
        this.datetimeFrom = datetimeFrom;
    }

    @Basic
    @Column(name = "datetime_to")
    public Timestamp getDatetimeTo() {
        return datetimeTo;
    }

    public void setDatetimeTo(Timestamp datetimeTo) {
        this.datetimeTo = datetimeTo;
    }

    @Basic
    @Column(name = "value")
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionEntity that = (ProductionEntity) o;
        return value == that.value &&
                Objects.equals(id, that.id) &&
                Objects.equals(machineName, that.machineName) &&
                Objects.equals(variableName, that.variableName) &&
                Objects.equals(datetimeFrom, that.datetimeFrom) &&
                Objects.equals(datetimeTo, that.datetimeTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, machineName, variableName, datetimeFrom, datetimeTo, value);
    }
}
