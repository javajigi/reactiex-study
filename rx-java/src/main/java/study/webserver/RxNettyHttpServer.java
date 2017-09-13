package study.webserver;

import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

public class RxNettyHttpServer {
    private static final Observable<String> RESPONSE_OK = Observable.just("OK");
    public static void main(String[] args) {
        
        HttpServer
            .newServer(8000)
            .start((req, resp) -> {
                return resp.setHeader("Content-Length", 2)
                .writeStringAndFlushOnEach(RESPONSE_OK);
            }).awaitShutdown();
    }
}
