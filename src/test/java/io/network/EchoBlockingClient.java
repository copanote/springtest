package io.network;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Random;

public class EchoBlockingClient {

    @Test
    void test() {

        System.out.println("start client");

        final int PORT = 5555;
        final String IP = "127.0.0.1";
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        ByteBuffer helloBuf = ByteBuffer.wrap("Hello !".getBytes());
        ByteBuffer randomBuffer;
        CharBuffer charBuffer;
        Charset charset = Charset.defaultCharset();
        CharsetDecoder decoder = charset.newDecoder();

        try (SocketChannel socketChannel = SocketChannel.open()) {

            if (socketChannel.isOpen()) {
                socketChannel.configureBlocking(true);
                socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024); //rcv buffer 크기
                socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 4 * 1024);
                /*
                SO_KEEPALIVE: 유휴 상태의 TCP 연결이 끊어졌는지 주기적으로 확인하여 연결 상태를 관리합니다.
                SO_LINGER: 소켓을 닫을 때, 버퍼에 남아있는 데이터를 지정된 시간 동안 전송하도록 대기하여 데이터 손실을 방지합니다.
                 */
                socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
                socketChannel.setOption(StandardSocketOptions.SO_LINGER, 5);

                socketChannel.connect(new InetSocketAddress(IP, PORT));

                if (socketChannel.isConnected()) {
                    socketChannel.write(helloBuf);


                    while (socketChannel.read(buffer) != -1) {
                        buffer.flip();

                        charBuffer = decoder.decode(buffer);
                        System.out.println(charBuffer.toString());

                        if (buffer.hasRemaining()) {
                            buffer.compact();
                        } else {
                            buffer.clear();
                        }

                        int r = new Random().nextInt(100);
                        if (r == 50) {
                            System.out.println("50 was generated! Close socket channel!");
                            break;
                        } else {
                            randomBuffer = ByteBuffer.wrap("Random Number: ".concat(String.valueOf(r)).getBytes());
                            socketChannel.write(randomBuffer);
                        }


                    }
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
