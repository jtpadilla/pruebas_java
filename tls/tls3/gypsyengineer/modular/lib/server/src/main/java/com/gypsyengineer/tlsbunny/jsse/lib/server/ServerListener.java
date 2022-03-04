package com.gypsyengineer.tlsbunny.jsse.lib.server;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;

public class ServerListener implements AutoCloseable, Runnable {

    private static final int PORT = 4443;

    static final private String[] ENABLED_PROTOCOLS = new String[] {
            "TLSv1.3"
    };

    static final private String[] ENABLED_CLIPHER_SUITS = new String[] {
            "TLS_AES_128_GCM_SHA256"
    };

    final private SSLServerSocket sslServerSocket;

    public ServerListener() throws IOException {
        ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
        sslServerSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(PORT);
        sslServerSocket.setEnabledProtocols(ENABLED_PROTOCOLS);
        sslServerSocket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
    }

    @Override
    public void close() throws Exception {
        if (sslServerSocket != null && !sslServerSocket.isClosed()) {
            sslServerSocket.close();
        }
    }

    @Override
    public void run() {
        while(true) {
            nextAccept();
        }
    }

    public void nextAccept() {
        try {
            SSLSocket socket = (SSLSocket) sslServerSocket.accept();
            new Thread(new ServerSession(socket)).start();
        } catch (Exception e) {
            System.out.printf("SERVER-LISTENER: Exception=%s%n", e.getMessage());
        }
        System.out.println("SERVER-LISTENER: Se detiene el servidor.");
    }

}