package study.concurrent;

public class Passenger {

    private long id;

    public Passenger(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Passenger [id=" + id + "]";
    }
}
