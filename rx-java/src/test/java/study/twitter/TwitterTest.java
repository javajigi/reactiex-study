package study.twitter;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.reactivex.disposables.Disposable;

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
        
        Disposable disposable = twitter.observe().subscribe(status -> {
            System.out.println("Status : " + status);
        }, ex -> {
            System.out.println("Exception : " + ex.getMessage());
        }, () -> {
            System.out.println("Completed");
        });
     
        TimeUnit.SECONDS.sleep(2);
        disposable.dispose();
        TimeUnit.SECONDS.sleep(2);
    }
}
