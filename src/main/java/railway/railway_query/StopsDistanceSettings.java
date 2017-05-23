package railway.railway_query;

public class StopsDistanceSettings {
    private Integer maxStops = null;
    private Integer exactStops = null;
    private Integer maxDistance = null;

    public void setMaxStops(Integer maxStops) {
        this.maxStops = maxStops;
    }

    public Integer getMaxStops() {
        return maxStops;
    }

    public void setExactStops(Integer exactStops) {
        this.exactStops = exactStops;
    }

    public Integer getExactStops() {
        return exactStops;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }
}