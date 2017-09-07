package observer;

public class MyMain {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        new CurrentConditionDisplay(weatherData);
        
        System.out.println("-----날씨가 변한다.----");
        weatherData.setMeasurements(40, 50, 10);
    }
}
