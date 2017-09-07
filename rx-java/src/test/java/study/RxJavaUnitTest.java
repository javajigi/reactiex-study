package study;

import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJavaUnitTest {
    @Test
    public void returnAValue(){
        Observable<String> observable = Observable.just("how", "to", "do", "in", "java");
        observable.map(w -> w.toUpperCase()).subscribe(System.out::println);
    }
    
    @Test
	public void observer() throws Exception {
    	Observable<String> observable = Observable.just("how", "to", "do", "in", "java");
    	
    	Observer<String> observer = new Observer<String>() {
			@Override
			public void onSubscribe(Disposable d) {
				
			}

			@Override
			public void onNext(String word) {
				System.out.println("word : " + word);
			}

			@Override
			public void onError(Throwable e) {
				
			}

			@Override
			public void onComplete() {
			}
    		
    	};
    	observable.map(w -> w.toUpperCase()).subscribe(observer);
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