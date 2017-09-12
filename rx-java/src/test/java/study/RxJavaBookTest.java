package study;

import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class RxJavaBookTest {
    @Test
    public void helloworld_memory_data() throws Exception {
        Observable.create(s -> {
            s.onNext("Hello World");
            s.onCompleted();
        }).subscribe(System.out::println);
    }

    @Test
    public void iterable() throws Exception {
        Observable<Integer> o = Observable.create(s -> {
            s.onNext(1);
            s.onNext(2);
            s.onNext(3);
            s.onCompleted();
        });

        o.map(n -> "Number : " + n).subscribe(System.out::println);
    }

    @Test
    public void observer() throws Exception {
        Observable<Integer> o = Observable.create(s -> {
            s.onNext(1);
            s.onNext(2);
            s.onNext(3);
            s.onCompleted();
        });
        
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                
            }

            @Override
            public void onError(Throwable e) {
                
            }

            @Override
            public void onNext(Integer t) {
                System.out.println(t);
            }
            
        };
        o.subscribe(observer);
    }
    
    @Test
    public void subscriber() throws Exception {
        Observable<Integer> o = Observable.create(s -> {
            s.onNext(1);
            s.onNext(2);
            s.onNext(3);
            s.onCompleted();
        });
        
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                
            }

            @Override
            public void onError(Throwable e) {
                
            }

            @Override
            public void onNext(Integer t) {
                if (t > 2) {
                    unsubscribe();
                } else {
                    System.out.println(t);
                }
            }
            
        };
        o.subscribe(subscriber);
    }
}
