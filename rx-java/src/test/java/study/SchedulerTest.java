package study;

import static org.junit.Assert.*;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SchedulerTest {
    private static void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
    
    Observable<String> simple() {
        return Observable.create(sub -> {
           log("Subscribed");
           sub.onNext("A");
           sub.onNext("B");
           sub.onComplete();
        });
    }
    
    @Test
    public void subscribeOn() throws Exception {
        log("Starting");
        final Observable<String> obs = simple();
        log("Created");
        final Observable<String> obs2 = obs
                .map(x -> x)
                .filter(x -> true);
        log("Transformed");
        
        obs2
            .subscribeOn(Schedulers.io())
            .subscribe(
                x -> log("Got : " + x),
                Throwable::printStackTrace,
                () -> log("Completed"));
        log("Exiting");
        Thread.sleep(1000);
    }
}
