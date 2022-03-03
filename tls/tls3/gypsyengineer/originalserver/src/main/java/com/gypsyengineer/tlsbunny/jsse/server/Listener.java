package com.gypsyengineer.tlsbunny.jsse.server;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;

public class Listener implements AutoCloseable, Runnable {

    private static final int PORT = 4443;

    private static final String[] ENABLED_PROTOCOLS = new String[] {
            "TLSv1.3"
    };

    private static final String[] ENABLED_CLIPHER_SUITS = new String[] {
            "TLS_AES_128_GCM_SHA256"
    };

    private final SSLServerSocket sslServerSocket;

    public Listener() throws IOException {
        ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
        sslServerSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(PORT);
        sslServerSocket.setEnabledProtocols(ENABLED_PROTOCOLS);
        sslServerSocket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
        System.out.printf("LISTENER: Se ha iniciado el servidor en el puerto %d%n", PORT);
    }

    @Override
    public void close() throws Exception {
        if (sslServerSocket != null && !sslServerSocket.isClosed()) {
            sslServerSocket.close();
        }
    }

    @Override
    public void run() {
        nextAccept();
    }

    public void nextAccept() {
        try (SSLSocket socket = (SSLSocket) sslServerSocket.accept()) {
            Session session = new Session(socket);
            session.run();
            //new Thread(new Session(socket)).start();
        } catch (Exception e) {
            System.out.printf("LISTENER Exception: %s%n", e.getMessage());
        }
        System.out.println("LISTENER Se detiene el servidor.");
    }

}
