package pl.fox.ogel_db.model;

public class OeeDataEntity {

    private Float performance;
    private Float availability;
    private Float quality;

    public OeeDataEntity(Float performance, Float availability, Float quality) {
        this.performance = performance;
        this.availability = availability;
        this.quality = quality;
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
