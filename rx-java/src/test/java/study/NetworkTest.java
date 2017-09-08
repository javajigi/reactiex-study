package study;

import static org.junit.Assert.*;

import java.nio.charset.Charset;

import org.junit.Test;

import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.client.HttpClient;

public class NetworkTest {
	@Test
	public void netty() throws Exception {
	    HttpClient.newClient("fierce-cove-29863.herokuapp.com", 80)
        .enableWireLogging("hello-client", LogLevel.INFO)
        .createGet("/getAllCricketFans")
        //.doOnNext(resp -> System.out.println(resp.toString()))
        .flatMap(resp -> resp.getContent()
                             .map(bb -> bb.toString(Charset.defaultCharset())))
        .toBlocking()
        .forEach(System.out::println);
	}
	
	@Test
    public void zip() throws Exception {
        
    }
	
	
}
