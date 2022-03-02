package com.gypsyengineer.tlsbunny.jsse.server;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.logging.Logger;

public class TLSv13Server {

    final static private Logger LOGGER = Logger.getLogger("server");

    private static final int DELAY_MILLIS = 1000;
    private static final int PORT = 4443;

    private static final String[] ENABLED_PROTOCOLS = new String[] {
            "TLSv1.3"
    };

    private static final String[] ENABLED_CLIPHER_SUITS = new String[] {
            "TLS_AES_128_GCM_SHA256"
    };

    public static void main(String[] args) throws Exception {
        ServerListener server = ServerListener.create();
        new Thread(server).start();
        Thread.sleep(DELAY_MILLIS);
    }

    static public class ServerListener implements Runnable, AutoCloseable {

        private final SSLServerSocket sslServerSocket;

        private ServerListener(SSLServerSocket sslServerSocket) {
            this.sslServerSocket = sslServerSocket;
        }

        public int port() {
            return sslServerSocket.getLocalPort();
        }

        @Override
        public void close() throws IOException {
            if (sslServerSocket != null && !sslServerSocket.isClosed()) {
                sslServerSocket.close();
            }
        }

        @Override
        public void run() {
            System.out.printf("server started on port %d%n", port());
            try (SSLSocket socket = (SSLSocket) sslServerSocket.accept()) {
                new Thread(new ServerConnection(socket)).run();
            } catch (Exception e) {
                System.out.printf("exception: %s%n", e.getMessage());
            }
            System.out.println("server stopped");
        }

        public static ServerListener create() throws IOException {
            ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
            SSLServerSocket socket = (SSLServerSocket) serverSocketFactory.createServerSocket(PORT);
            socket.setEnabledProtocols(ENABLED_PROTOCOLS);
            socket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
            return new ServerListener(socket);
        }

    }

}