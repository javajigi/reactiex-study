package study;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.reactivex.Observable;

public class ObservableTest {
    private static void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
    
    @Test
    public void timer() throws Exception {
        Observable.timer(3, TimeUnit.SECONDS)
            .subscribe((Long zero) -> log(zero));
    }
    
    @Test
    public void create() throws Exception {
        log("Before");
        Observable
            .range(3,  5)
            .subscribe(i -> log(i));
        log("After");
    }
    
    @Test
    public void create_thread() throws Exception {
        Observable<Integer> ints = Observable.<Integer>create(s -> {
           log("Create");
           s.onNext(5);
           s.onNext(6);
           s.onNext(7);
           s.onComplete();
           log("Completed");
        }).cache();
        
        log("Starting");
        ints.subscribe(i -> log("Element A: " + i));
        ints.subscribe(i -> log("Element B: " + i));
        log("Exit");
    }
}
