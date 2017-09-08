package study.twitter;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterSubject {
    private final PublishSubject<Status> subject = PublishSubject.create();
    
    public TwitterSubject() {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(new StatusListener() {
            
            @Override
            public void onException(Exception ex) {
                subject.onError(ex);
            }
            
            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                
            }
            
            @Override
            public void onStatus(Status status) {
                subject.onNext(status);
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
    }
    
    public Observable<Status> observe() {
        return subject;
    }
}
