package study.twitter;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import io.reactivex.Observable;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class Twitter {
    Observable<Status> observe() {
        return Observable.create(s -> {
            System.out.println("Created");
            TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
            twitterStream.addListener(new StatusListener() {
                
                @Override
                public void onException(Exception ex) {
                    if (s.isDisposed()) {
                        System.out.println("ex disposed shutdown");
                        twitterStream.shutdown();
                    } else {
                        s.onError(ex);
                    }
                }
                
                @Override
                public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                    
                }
                
                @Override
                public void onStatus(Status status) {
                    if (s.isDisposed()) {
                        System.out.println("next disposed shutdown");
                        twitterStream.shutdown();
                    } else {
                        s.onNext(status);
                    }
                }
                
                @Override
                public void onStallWarning(StallWarning warning) {
                    
                }
                
                @Override
                public void onScrubGeo(long userId, long upToStatusId) {
                    
                }
                
                @Override
                public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                    
                }
            });
            
            twitterStream.sample();
        });
    }
    
    void consume(Consumer<Status> onStatus, Consumer<Exception> onException) throws InterruptedException {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(new StatusListener() {
            
            @Override
            public void onException(Exception ex) {
                onException.accept(ex);
            }
            
            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                
            }
            
            @Override
            public void onStatus(Status status) {
                onStatus.accept(status);
            }
            
            @Override
            public void onStallWarning(StallWarning warning) {
                
            }
            
            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                
            }
            
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                
            }
        });
        
        twitterStream.sample();
        TimeUnit.SECONDS.sleep(10);
        twitterStream.shutdown();
    }

    public void sample() throws InterruptedException {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(new StatusListener() {
            
            @Override
            public void onException(Exception ex) {
                System.out.println("Error callback : " + ex.getMessage());
            }
            
            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                
            }
            
            @Override
            public void onStatus(Status status) {
                System.out.println("Status : " + status);
            }
            
            @Override
            public void onStallWarning(StallWarning warning) {
                
            }
            
            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                
            }
            
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                
            }
        });
        
        twitterStream.sample();
        TimeUnit.SECONDS.sleep(10);
        twitterStream.shutdown();
    }

}
