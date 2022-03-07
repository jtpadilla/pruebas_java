package com.gypsyengineer.tlsbunny.jsse.client;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

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

    public ClientSession(String host, int port) throws Exception {

        //SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocketFactory sslSocketFactory = SSLUtil.getSocketFactory();

        socket = (SSLSocket) sslSocketFactory.createSocket(host, port);
        System.out.println("WantClientAuth:" + socket.getWantClientAuth());
        System.out.println("NeedClientAuth:" + socket.getNeedClientAuth());
        System.out.println("UseClientMode:" + socket.getUseClientMode());
        System.out.println("EnableSessionCreation:" + socket.getEnableSessionCreation());
        socket.setEnabledProtocols(ENABLED_PROTOCOLS);
        socket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
        is = new BufferedInputStream(socket.getInputStream());
        os = new BufferedOutputStream(socket.getOutputStream());
    }

    public void sendMessage(String message) throws IOException {

        // Envio
        writeMessage(message);

        // Recepcion
        Optional<String> received = readMessage();

        // Verificacion
        if (received.isPresent()) {
            System.out.println("El eco del emnsaje es: " + (received.get().equals(message) ? "OK" : "ERROR"));
        } else {
            throw new IOException("No se han ecibido datos");
        }

    }

    private Optional<String> readMessage() throws IOException {
        byte[] data = new byte[2048];
        int len = is.read(data);
        if (len <= 0) {
            return Optional.empty();
        } else {
            return Optional.of(new String(data, 0, len));
        }
    }

    private void writeMessage(String msg) throws IOException {
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        os.write(bytes, 0, bytes.length);
        os.flush();
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }

}
