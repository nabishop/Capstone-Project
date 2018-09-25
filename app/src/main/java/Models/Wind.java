package Models;

public class Wind {
    private String windDirectionDegrees;
    private String windDirectionCompass;
    private String hour;
    private double windSpeedMph;

    public Wind(String windDirectionDegrees, String windDirectionCompass, String hour,
                double windSpeedMph) {
        this.windDirectionDegrees = windDirectionDegrees;
        this.windDirectionCompass = windDirectionCompass;
        this.hour = hour;
        this.windSpeedMph = windSpeedMph;
    }

    public double getWindSpeedMph() {
        return windSpeedMph;
    }

    public String getHour() {
        return hour;
    }

    public String getWindDirectionCompass() {
        return windDirectionCompass;
    }

    public String getWindDirectionDegrees() {
        return windDirectionDegrees;
    }

}
