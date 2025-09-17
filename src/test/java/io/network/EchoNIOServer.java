package io.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EchoNIOServer {

    private Map<SocketChannel, List<byte[]>> keepDataTrack = new HashMap<>();
    private ByteBuffer buffer = ByteBuffer.allocate(2 * 1024);

    private void startEchoServer() {
        final int DEFAULT_PORT = 5555;

        //open()메서드를 호출해서 Selector와 ServerSocketChannel을 연다.
        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            //셀렉터와 서버소켓 채널이 성공적으로 열렸는지 확인한다.
            if (serverSocketChannel.isOpen() && selector.isOpen()) {
                //논블로킹 모드로 설정한다.

                serverSocketChannel.configureBlocking(false);

                serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 256 * 1024);
                serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                serverSocketChannel.bind(new InetSocketAddress(DEFAULT_PORT));

                //현재 채널을 주어진 셀렉터에 등록한다.
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                System.out.println("Waiting for connections...");


                while (true) {
                    //유입 이벤트를 기다린다.
                    selector.select();

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        iterator.remove();

                        if (!key.isValid()) {
                            continue;
                        }

                        if (key.isAcceptable()) {
                            //acceptOP
                        } else if (key.isReadable()) {
                            //readOP
                        } else if (key.isWritable()) {
                            //writeOP
                        }
                    }
                }
            } else {
                System.out.println("The Server socket channel or selector cannot be opened!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
    }
}
