package study.twitter;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import rx.Subscription;

public class TwitterTest {
    int count = 0;
    
    @Test
    public void twitter() throws Exception {
        Twitter twitter = new Twitter();
        twitter.sample();
    }
    
    @Test
    public void consume() throws Exception {
        Twitter twitter = new Twitter();
        twitter.consume(status -> {
            System.out.println("Status : " + status);
        }, ex -> {
            System.out.println("Exception : " + ex.getMessage());
        });
    }
    
    @Test
    public void observe() throws Exception {
        Twitter twitter = new Twitter();
        
        Subscription subscription = twitter.observe().subscribe(status -> {
            System.out.println("Status : " + status);
        }, ex -> {
            System.out.println("Exception : " + ex.getMessage());
        }, () -> {
            System.out.println("Completed");
        });
     
        TimeUnit.SECONDS.sleep(2);
        subscription.unsubscribe();
        TimeUnit.SECONDS.sleep(2);
    }
}
