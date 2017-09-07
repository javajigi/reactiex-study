package observer;

import java.util.Observable;

public class WeatherData extends Observable {
    private float temperature; // 온도
    private float humidity; // 습도
    private float pressure; // 기압

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public void measurementsChanged() {
        setChanged();
        notifyObservers();
    }

    // 온도값 반환
    public float getTemperature() {
        return temperature;
    }

    // 습도값 반환
    public float getHumidity() {
        return humidity;
    }

    // 기압값 반환
    public float getPressure() {
        return pressure;
    }
}
