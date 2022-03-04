package com.gypsyengineer.tlsbunny.jsse.client;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class ClientSession implements AutoCloseable {

    private static final String[] ENABLED_PROTOCOLS = new String[] {
            "TLSv1.3"
    };

    private static final String[] ENABLED_CLIPHER_SUITS = new String[] {
            "TLS_AES_128_GCM_SHA256"
    };

    final private SSLSocket socket;
    final private InputStream is;
    final private OutputStream os;

    public ClientSession(String host, int port) throws IOException {

        socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);

        socket.setEnabledProtocols(ENABLED_PROTOCOLS);
        socket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);

        System.out.println("WantClientAuth:" + socket.getWantClientAuth());
        socket.setWantClientAuth(socket.getWantClientAuth());

        System.out.println("NeedClientAuth:" + socket.getNeedClientAuth());
        socket.setNeedClientAuth(socket.getNeedClientAuth());

        System.out.println("UseClientMode:" + socket.getUseClientMode());
        socket.setUseClientMode(socket.getUseClientMode());

        System.out.println("EnableSessionCreation:" + socket.getEnableSessionCreation());
        socket.setEnableSessionCreation(socket.getEnableSessionCreation());

        is = new BufferedInputStream(socket.getInputStream());
        os = new BufferedOutputStream(socket.getOutputStream());
    }

    public void sendMessage(String message) throws IOException {
        os.write(message.getBytes());
        os.flush();
        byte[] data = new byte[2048];
        int len = is.read(data);
        if (len <= 0) {
            throw new IOException("no data received");
        }
        System.out.printf("CLIENT-SESSION: Ha recibido %d bytes: %s%n", len, new String(data, 0, len));
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }

}
