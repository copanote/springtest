package io.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoBlockingServer {
    public static void main(String[] args) {

        final int PORT = 5555;
        final String IP = "127.0.0.1";

        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            if (serverSocketChannel.isOpen()) {

                serverSocketChannel.configureBlocking(true);
                serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024); //rcv buffer 크기

                /**
                 일반적으로 소켓 연결을 종료하면 운영체제는 해당 포트를 TIME_WAIT 상태로 일정 시간(보통 1분) 동안 유지합니다.
                 이 기간 동안에는 동일한 IP와 포트 조합으로 새로운 소켓을 바인딩할 수 없습니다.
                 이는 TCP/IP 연결이 완전히 종료되었음을 보장하기 위한 안전장치입니다.

                 하지만 서버 애플리케이션을 재시작할 때, 이 TIME_WAIT 상태 때문에 서버가 이전에 사용하던 포트를 즉시 다시 사용할 수 없어
                 BindException과 같은 오류가 발생할 수 있습니다.

                 SO_REUSEADDR 옵션을 true로 설정하면, TIME_WAIT 상태의 포트를 즉시 재사용하여 새로운 소켓을 바인딩할 수 있습니다.
                 이는 서버 애플리케이션의 빠른 재시작을 가능하게 해줍니다.

                 주의할 점: 이 옵션은 일부 상황에서 잠재적인 문제를 일으킬 수도 있지만,
                 대부분의 서버 애플리케이션에서는 빠른 재시작을 위해 기본적으로 true로 설정하는 것이 일반적입니다.
                 */
                serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);  //주소재사용
                serverSocketChannel.bind(new InetSocketAddress(IP, PORT));

                System.out.println("Waiting for connections...");


                while (true) {
                    try (SocketChannel socketChannel = serverSocketChannel.accept()) {
                        System.out.println("Incoming connection from: " + socketChannel.getRemoteAddress());

                        while (socketChannel.read(buffer) != -1) {
                            buffer.flip();

                            socketChannel.write(buffer);

                            if (buffer.hasRemaining()) {
                                buffer.compact();
                            } else {
                                buffer.clear();
                            }
                        }

                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
