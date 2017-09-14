package study.webserver;

import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;
import study.twitter.Twitter;

public class RxNettyTwitterServer {
    public static void main(String[] args) {
        HttpServer
            .newServer(8000)
            .start((req, resp) -> {
                Twitter twitter = new Twitter();
                Observable<String> obs = 
                        twitter.observe()
                        .map(status -> status.toString())
                        .limit(2);
                return resp
                        .addHeader("Content-Type", "application/json;charset=UTF-8")
                        .writeStringAndFlushOnEach(obs);
            }).awaitShutdown();
    }
}
