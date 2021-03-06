package pl.fox.ogel_db.model;

public class RuntimeDataEntity {

    private Float downTimePercentage;

    public RuntimeDataEntity(Float downTimePercentage) {
        this.downTimePercentage = downTimePercentage;
    }

    public Float getDownTimePercentage() {
        return downTimePercentage;
    }

    public void setDownTimePercentage(Float downTimePercentage) {
        this.downTimePercentage = downTimePercentage;
    }
}
