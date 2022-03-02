package com.gypsyengineer.tlsbunny.jsse.server;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.logging.Logger;

public class ServerListener {

    static final private Logger LOGGER = Logger.getLogger("server.listener");

    static final private String[] ENABLED_PROTOCOLS = new String[] {
            "TLSv1.3"
    };

    static final private String[] ENABLED_CLIPHER_SUITS = new String[] {
            "TLS_AES_128_GCM_SHA256"
    };

    private static final int PORT = 4443;

    static private SSLServerSocket buildServerSocket() throws IOException {
        ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(PORT);
        sslServerSocket.setEnabledProtocols(ENABLED_PROTOCOLS);
        sslServerSocket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
        return sslServerSocket;
    }

    static public void runListenerForever() throws IOException {

        LOGGER.info(String.format("Se inicia el servidor en el puerto %d%n", PORT));
        SSLServerSocket sslServerSocket = buildServerSocket();

        while (true) {
            try (SSLSocket socket = (SSLSocket) sslServerSocket.accept()) {
                new Thread(new ServerConnection(socket)).start();
            } catch (Exception e) {
                System.out.printf("exception: %s%n", e.getMessage());
            }
        }

    }

}