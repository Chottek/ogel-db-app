package pl.fox.ogel_db.model;

public class OEEData {

    private String machine_name;
    private Float performance;
    private Float availability;
    private Float quality;

    public OEEData(String machine_name, Float performance, Float availability, Float quality) {
        this.machine_name = machine_name;
        this.performance = performance;
        this.availability = availability;
        this.quality = quality;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public Float getPerformance() {
        return performance;
    }

    public void setPerformance(Float performance) {
        this.performance = performance;
    }

    public Float getAvailability() {
        return availability;
    }

    public void setAvailability(Float availability) {
        this.availability = availability;
    }

    public Float getQuality() {
        return quality;
    }

    public void setQuality(Float quality) {
        this.quality = quality;
    }
}
