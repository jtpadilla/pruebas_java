package com.baeldung.ssl.example.server;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleServer {

    static final String[] ENABLED_CLIPHER_SUITS = new String[] { "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256" };
    static final String[] ENABLED_PROTOCOLS = new String[] { "TLSv1.2" };

    static void startServer(int port) throws IOException {

        ServerSocketFactory factory = SSLServerSocketFactory.getDefault();

        try (SSLServerSocket listener = (SSLServerSocket) factory.createServerSocket(port)) {
            listener.setNeedClientAuth(true);
            listener.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
            listener.setEnabledProtocols(ENABLED_PROTOCOLS);
            while (true) {
                try (Socket socket = listener.accept()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Hello World!");
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        startServer(8443);
    }

}