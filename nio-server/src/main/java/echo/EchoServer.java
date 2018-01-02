package echo;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {
    private static final String POISON_PILL = "POISON_PILL";

    private static Selector selector = null;

    public static void main(String[] args) throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 5454));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(256);

        Thread thread = new Thread() {
            @Override
            public void run() {
                String receiveData = null;

                while (true) {
                    SelectionKey key = null;
                    try {
                        int keyCount = selector.select();

                        System.out.println("key count : " + keyCount);

                        if (keyCount == 0) {
                            continue;
                        }

                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iter = selectedKeys.iterator();
                        while (iter.hasNext()) {
                            key = iter.next();

                            if (key.isAcceptable()) {
                                register(selector, serverSocket);
                            }

                            if (key.isReadable()) {
                                receiveData = receive(key);
                                System.out.println("receive data : " + receiveData);
                            }

                            if (key.isWritable()) {
                                send(receiveData, key);
                            }

                            iter.remove();
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        SocketChannel client = (SocketChannel) key.channel();
                        key.cancel();
                        try {
                            client.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        };

        thread.start();
    }

    private static void answerWithEcho(ByteBuffer buffer, SelectionKey key)
            throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        int bytesread = client.read(buffer);
        if (bytesread == -1) {
            key.cancel();
            client.close();
            return;
        }

        if (new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        }
        key.interestOps(SelectionKey.OP_WRITE);

        buffer.flip();
        client.write(buffer);
        key.interestOps(SelectionKey.OP_READ);
        buffer.clear();
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private static String receive(SelectionKey key)
            throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        SocketChannel client = (SocketChannel) key.channel();
        int bytesread = client.read(buffer);
        if (bytesread == -1) {
            throw new IOException("client close!");
        }

        if (new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        }
        key.interestOps(SelectionKey.OP_WRITE);
        return new String(buffer.array()).trim();
    }

    private static void send(String data, SelectionKey key) throws IOException {
        System.out.println("send data : " + data);

        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.wrap(data.getBytes());
        client.write(byteBuffer);
        key.interestOps(SelectionKey.OP_READ);
        byteBuffer.clear();

//        key.cancel();
//        client.close();
    }

    public static Process start() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = EchoServer.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);

        return builder.start();
    }
}
