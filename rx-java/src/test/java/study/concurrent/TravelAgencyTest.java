package study.concurrent;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TravelAgencyTest {
    @Test
    public void bookTicket() throws Exception {
        TravelAgency ta = new TravelAgency();
        Observable<Flight> flight = ta.rxLookupFlight("LOT 783").subscribeOn(Schedulers.io());
        Observable<Passenger> passenger = ta.rxFindPassenger(42).subscribeOn(Schedulers.io());
        Observable<Ticket> ticket = 
                flight.zipWith(passenger, (f, p) -> ta.bookTicket(flight, passenger));
        ticket.subscribe(ta::sendEmail);
        
        Thread.sleep(5000);
    }
    
    @Test
    public void bookTicket_zipWith() throws Exception {
        TravelAgency ta = new TravelAgency();
        Observable<Flight> flight = ta.rxLookupFlight("LOT 783").subscribeOn(Schedulers.io());
        Observable<Passenger> passenger = ta.rxFindPassenger(42).subscribeOn(Schedulers.io());
        Observable<Ticket> ticket = 
                flight.zipWith(passenger, (f, p) -> Pair.of(f, p))
                .flatMap(pair -> ta.rxBookTicket(pair.getLeft(), pair.getRight()));
        ticket.subscribe(ta::sendEmail);
        
        Thread.sleep(5000);
    }
    
    @Test
    public void bookTicket_flatMap() throws Exception {
        TravelAgency ta = new TravelAgency();
        Observable<Flight> flight = ta.rxLookupFlight("LOT 783").subscribeOn(Schedulers.io());
        Observable<Passenger> passenger = ta.rxFindPassenger(42).subscribeOn(Schedulers.io());
        Observable<Ticket> ticket = 
                flight.zipWith(passenger, ta::rxBookTicket)
                .flatMap(obs -> obs);
        ticket.subscribe(ta::sendEmail);
        
        Thread.sleep(4000);
    }
    
    @Test
    public void sendEmails() throws Exception {
        List<Ticket> tickets = Arrays.asList(
                new Ticket(new Flight("abc 104"), new Passenger(1L)), 
                new Ticket(new Flight("error 243"), new Passenger(3L)), 
                new Ticket(new Flight("gde 284"), new Passenger(2L)));
        TravelAgency ta = new TravelAgency();
        ta.sendEmails(tickets);
        
        Thread.sleep(4000);
    }

}
