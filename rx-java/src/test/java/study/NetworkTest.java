package study;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

import org.junit.Test;

import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.client.HttpClient;

public class NetworkTest {
	@Test
	public void netty() throws Exception {
		InetAddress addr = InetAddress.getByName("fierce-cove-29863.herokuapp.com");
	    int port = 80;
	    SocketAddress sockaddr = new InetSocketAddress(addr, port);
		HttpClient.newClient(sockaddr)
        .enableWireLogging("hello-client", LogLevel.ERROR)
        .createGet("/getAllCricketFans")
        .doOnNext(resp -> System.out.println(resp.toString()))
        .flatMap(resp -> resp.getContent()
                             .map(bb -> bb.toString(Charset.defaultCharset())))
        .toBlocking()
        .forEach(System.out::println);
	}
}
