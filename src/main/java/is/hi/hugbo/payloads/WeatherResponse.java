package is.hi.hugbo.payloads;

import java.time.LocalDateTime;

public class WeatherResponse {
  private LocalDateTime date;
  private int windspeed;
  private String direction;
  private int temperature;

  public WeatherResponse() {
    this.windspeed = 0;
    this.direction = "";
    this.temperature = 0;
    this.date = LocalDateTime.MIN;
  }

  /**
   * Constructor
   * 
   * @param date LocalDateTime date of the weather forecast
   * @param F    itn wind speed in m/s
   * @param D    String wind direction
   * @param T    int temperature in celsius
   */
  public WeatherResponse(LocalDateTime date, int F, String D, int T) {
    this.windspeed = F;
    this.direction = D;
    this.temperature = T;
    this.date = date;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public int getWindspeed() {
    return windspeed;
  }

  public void setWindspeed(int F) {
    this.windspeed = F;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String D) {
    this.direction = D;
  }

  public int getTemperature() {
    return temperature;
  }

  public void setTemperature(int T) {
    this.temperature = T;
  }
}
