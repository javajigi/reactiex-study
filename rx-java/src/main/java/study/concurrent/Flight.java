package study.concurrent;

public class Flight {
    private String flightNo;

    public Flight(String flightNo) {
        this.flightNo = flightNo;
    }
    
    public boolean hasError() {
        return flightNo.contains("error");
    }

    @Override
    public String toString() {
        return "Flight [flightNo=" + flightNo + "]";
    }
}
