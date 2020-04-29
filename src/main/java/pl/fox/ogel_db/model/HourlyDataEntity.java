package pl.fox.ogel_db.model;

public class HourlyDataEntity {

    private String machineName;
    private Integer hour;
    private Integer value;

    public HourlyDataEntity(String machineName, Integer hour, Integer value) {
        this.machineName = machineName;
        this.hour = hour;
        this.value = value;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
