package pl.fox.ogel_db.model;

public class RuntimeData {   // Object that contains counted values from Runtime table from database

    private String machine_name;
    private Float total_runtime;
    private Float total_downtime;
    private Float runtime_percentage;
    private Float downtime_percentage;

    public RuntimeData(String machine_name, Float total_runtime, Float total_downtime, Float runtime_percentage, Float downtime_percentage) {
        this.machine_name = machine_name;
        this.total_runtime = total_runtime;
        this.total_downtime = total_downtime;
        this.runtime_percentage = runtime_percentage;
        this.downtime_percentage = downtime_percentage;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public Float getTotal_runtime() {
        return total_runtime;
    }

    public void setTotal_runtime(Float total_runtime) {
        this.total_runtime = total_runtime;
    }

    public Float getTotal_downtime() {
        return total_downtime;
    }

    public void setTotal_downtime(Float total_downtime) {
        this.total_downtime = total_downtime;
    }

    public Float getRuntime_percentage() {
        return runtime_percentage;
    }

    public void setRuntime_percentage(Float runtime_percentage) {
        this.runtime_percentage = runtime_percentage;
    }

    public Float getDowntime_percentage() {
        return downtime_percentage;
    }

    public void setDowntime_percentage(Float downtime_percentage) {
        this.downtime_percentage = downtime_percentage;
    }

}
