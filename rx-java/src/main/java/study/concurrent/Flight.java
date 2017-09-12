package study.concurrent;

public class Flight {
    private String flightNo;

    public Flight(String flightNo) {
        this.flightNo = flightNo;
    }

    @Override
    public String toString() {
        return "Flight [flightNo=" + flightNo + "]";
    }
}
