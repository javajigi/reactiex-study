package study.twitter;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.reactivex.disposables.Disposable;

public class TwitterSubjectTest {

    @Test
    public void subject() throws Exception {
        TwitterSubject subject = new TwitterSubject();
        Disposable disposable = subject.observe().subscribe(status -> {
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
