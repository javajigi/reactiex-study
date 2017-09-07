package study;

import static org.junit.Assert.*;

import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJavaUnitTest {
    String result="";

    @Test
    public void returnAValue(){
        Observable<String> observer = Observable.just("Hello"); // provides datea
        observer.subscribe(s -> result=s); // Callable as subscriber
        assertTrue(result.equals("Hello"));
    }
    
    @Test
    public void helloWorld() throws Exception {
        Flowable.just("Hello world").subscribe(System.out::println);
    }
    
    @Test
    public void consumer() throws Exception {
        Flowable.just("Hello world")
        .subscribe(new Consumer<String>() {
            @Override public void accept(String s) {
                System.out.println(s);
            }
        });
    }
    
    @Test
    public void thread() throws Exception {
        Flowable.fromCallable(() -> {
            Thread.sleep(1000);
            return "Done";
        })
          .subscribeOn(Schedulers.io())
          .observeOn(Schedulers.single())
          .subscribe(System.out::println, Throwable::printStackTrace);
        
        System.out.println("After Observable, Before Thread");
        Thread.sleep(2000);
        System.out.println("After Thread");
    }
    
    @Test
    public void thread_no_builder() throws Exception {
        Flowable<String> source = Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        });

        Flowable<String> runBackground = source.subscribeOn(Schedulers.io());

        Flowable<String> showForeground = runBackground.observeOn(Schedulers.single());

        showForeground.subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000);
    }
    
    @Test
    public void parallel1() throws Exception {
        Flowable.range(1, 10)
        .observeOn(Schedulers.computation())
        .map(v -> v * v)
        .blockingSubscribe(System.out::println);
    }
    
    @Test
    public void parallel2() throws Exception {
        Flowable.range(1, 10)
        .flatMap(v ->
            Flowable.just(v)
              .subscribeOn(Schedulers.computation())
              .map(w -> w * w)
        )
      .blockingSubscribe(System.out::println);
    }
    
    @Test
    public void parallel3() throws Exception {
        Flowable.range(1, 10)
        .parallel()
        .runOn(Schedulers.computation())
        .map(v -> v * v)
        .sequential()
        .blockingSubscribe(System.out::println);
    }
}