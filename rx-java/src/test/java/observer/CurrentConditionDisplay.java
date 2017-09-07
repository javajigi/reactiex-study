package observer;

import java.util.Observable;
import java.util.Observer;

public class CurrentConditionDisplay implements Observer, DisplayElement {
    Observable observable; // 등록될 Observable

    private float temperature; // 온도
    private float humidity;

    public CurrentConditionDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void display() {
        System.out.println("현재 온도 : " + temperature + "도,  현재 습도 : " + humidity + "%");
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof WeatherData) { // Observable이 WeatherData인지 확인
            WeatherData weatherData = (WeatherData) obs; // WeatherData로 변환
            this.temperature = weatherData.getTemperature(); // 온도 값 갱ㅇ신
            this.humidity = weatherData.getHumidity(); // 습도값 갱신
            display(); // 최신 값 출력
        }
    }
}
