package com.gypsyengineer.tlsbunny.jsse.client;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class TLSv13TestClient {

    private static final int DELAY = 1000; // in millis

    private static final String[] ENABLED_PROTOCOLS = new String[] {
            "TLSv1.3"
    };

    private static final String[] ENABLED_CLIPHER_SUITS = new String[] {
            "TLS_AES_128_GCM_SHA256"
    };

    private static final String message = "Like most of life's problems, this one can be solved with bending!";

    public static void main(String[] args) throws Exception {

        try (SSLSocket socket = createSocket("localhost", 4443)) {
            InputStream is = new BufferedInputStream(socket.getInputStream());
            OutputStream os = new BufferedOutputStream(socket.getOutputStream());
            os.write(message.getBytes());
            os.flush();
            byte[] data = new byte[2048];
            int len = is.read(data);
            if (len <= 0) {
                throw new IOException("no data received");
            }
            System.out.printf("======[CLIENTE]> Ha recibido %d bytes: %s%n", len, new String(data, 0, len));
        }

    }

    public static SSLSocket createSocket(String host, int port) throws IOException {
        SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);
        socket.setEnabledProtocols(ENABLED_PROTOCOLS);
        socket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
        return socket;
    }

}