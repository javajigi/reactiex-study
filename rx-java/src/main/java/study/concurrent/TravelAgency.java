package study.concurrent;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TravelAgency {
    private static void log(Object msg) {
        System.out.println("time : " + new Date() + " : " + Thread.currentThread().getName() + ": " + msg);
    }
    
    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    Flight lookupFlight(String flightNo) {
        sleep(1000);
        log("lookupFlight");
        return new Flight(flightNo);
    }
    
    Observable<Flight> rxLookupFlight(String flightNo) {
        return Observable.defer(() -> 
            Observable.just(lookupFlight(flightNo)));
    }
    
    Passenger findPassenger(long id) {
        sleep(2000);
        log("findPassenger");
        return new Passenger(id);
    }
    
    Observable<Passenger> rxFindPassenger(long id) {
        return Observable.defer(() -> 
            Observable.just(findPassenger(id)));
    }
    
    Ticket bookTicket(Observable<Flight> flight, Observable<Passenger> passenger) {
        log("bookTicket");
        return new Ticket();
    }
    
    Observable<Ticket> rxBookTicket(Flight flight, Passenger passenger) {
        log("rxBookTicket");
        return Observable.defer(() -> 
            Observable.just(new Ticket(flight, passenger)));
    }
    
    SmtpResponse sendEmail(Ticket ticket) {
        if (ticket.hasError()) {
            throw new IllegalArgumentException("error가 발생한 ticket입니다.");
        }
        
        return new SmtpResponse();
    }
    
    Observable<SmtpResponse> rxSendEmail(Ticket ticket) {
        return Observable.fromCallable(() -> sendEmail(ticket));
    }
    
    List<Ticket> sendEmails(List<Ticket> tickets) {
        log("send emails");
        return Observable.fromIterable(tickets)
            .doOnNext(ticket -> log(ticket))
            .flatMap(ticket -> 
                rxSendEmail(ticket)
                    .flatMap(response -> Observable.<Ticket>empty())
                    .doOnError(e -> log("Failed to send"))
                    .onErrorReturn(err -> ticket))
                    .subscribeOn(Schedulers.io())
            .toList()
            .blockingGet();
    }
}
