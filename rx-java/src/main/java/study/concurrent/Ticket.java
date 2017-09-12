package study.concurrent;

public class Ticket {
    private Flight flight;
    private Passenger passenger;
    
    public Ticket() {
    }

    public Ticket(Flight flight, Passenger passenger) {
        this.flight = flight;
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return "Ticket [flight=" + flight + ", passenger=" + passenger + "]";
    }
}
