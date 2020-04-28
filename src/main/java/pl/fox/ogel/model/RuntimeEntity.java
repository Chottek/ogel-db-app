package pl.fox.ogel.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "runtime")
public class RuntimeEntity {

    private Integer id;
    private String machineName;
    private Timestamp datetime;
    private byte isrunning;

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
    @Column(name = "datetime")
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "isrunning")
    public byte getIsrunning() {
        return isrunning;
    }

    public void setIsrunning(byte isrunning) {
        this.isrunning = isrunning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuntimeEntity that = (RuntimeEntity) o;
        return isrunning == that.isrunning &&
                Objects.equals(id, that.id) &&
                Objects.equals(machineName, that.machineName) &&
                Objects.equals(datetime, that.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, machineName, datetime, isrunning);
    }
}
