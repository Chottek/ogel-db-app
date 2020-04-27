package pl.fox.ogel_db.model;

public class ProductionData {   // Object that contains counted values from Production table from database

    private String machine_name;
    private Integer production_value;
    private Integer scrap_value;
    private Integer net_production;
    private Float gross_percentage;
    private Float scrap_percentage;
    private Double temperature_value;
    private Integer temperature_warning;
    private String date;

    public ProductionData(String machine_name, Integer production_value, Integer scrap_value,
                          Integer net_production, Float gross_percentage, Float scrap_percentage,
                            Double temperature_value, Integer temperature_warning, String date) {
        this.machine_name = machine_name;
        this.production_value = production_value;
        this.scrap_value = scrap_value;
        this.net_production = net_production;
        this.gross_percentage = gross_percentage;
        this.scrap_percentage = scrap_percentage;
        this.temperature_value = temperature_value;
        this.temperature_warning = temperature_warning;
        this.date = date;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public Integer getProduction_value() {
        return production_value;
    }

    public void setProduction_value(Integer production_value) {
        this.production_value = production_value;
    }

    public Integer getScrap_value() {
        return scrap_value;
    }

    public void setScrap_value(Integer scrap_value) {
        this.scrap_value = scrap_value;
    }

    public Integer getNet_production() {
        return net_production;
    }

    public void setNet_production(Integer net_production) {
        this.net_production = net_production;
    }

    public Float getGross_percentage() {
        return gross_percentage;
    }

    public void setGross_percentage(Float gross_percentage) {
        this.gross_percentage = gross_percentage;
    }

    public Float getScrap_percentage() {
        return scrap_percentage;
    }

    public void setScrap_percentage(Float scrap_percentage) {
        this.scrap_percentage = scrap_percentage;
    }

    public Double getTemperature_value() {
        return temperature_value;
    }

    public void setTemperature_value(Double temperature_value) {
        this.temperature_value = temperature_value;
    }

    public Integer getTemperature_warning() {
        return temperature_warning;
    }

    public void setTemperature_warning(Integer temperature_warning) {
        this.temperature_warning = temperature_warning;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
