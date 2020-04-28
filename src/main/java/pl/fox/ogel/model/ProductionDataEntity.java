package pl.fox.ogel.model;

public class ProductionDataEntity {   // Object that contains counted values from Production table from database

    private String machineName;
    private Integer netProductionValue;
    private Float scrapPercentage;
    private Float grossPercentage;
    private Integer warningValue;

    public ProductionDataEntity(String machineName, Integer netProductionValue, Float scrapPercentage,
                                    Float grossPercentage, Integer warningValue) {
        this.machineName = machineName;
        this.netProductionValue = netProductionValue;
        this.scrapPercentage = scrapPercentage;
        this.grossPercentage = grossPercentage;
        this.warningValue = warningValue;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Integer getNetProductionValue() {
        return netProductionValue;
    }

    public void setNetProductionValue(Integer netProductionValue) {
        this.netProductionValue = netProductionValue;
    }

    public Float getScrapPercentage() {
        return scrapPercentage;
    }

    public void setScrapPercentage(Float scrapPercentage) {
        this.scrapPercentage = scrapPercentage;
    }

    public Float getGrossPercentage() {
        return grossPercentage;
    }

    public void setGrossPercentage(Float grossPercentage) {
        this.grossPercentage = grossPercentage;
    }

    public Integer getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(Integer warningValue) {
        this.warningValue = warningValue;
    }
}
