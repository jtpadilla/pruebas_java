package com.gypsyengineer.tlsbunny.jsse.client;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
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

    public ClientSession(String host, int port) throws IOException {
        socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);
        socket.setEnabledProtocols(ENABLED_PROTOCOLS);
        socket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
        is = new BufferedInputStream(socket.getInputStream());
        os = new BufferedOutputStream(socket.getOutputStream());
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }

    public void writeLine(String line) throws IOException {
        os.write(line.getBytes());
        os.flush();
    }

    public Optional<String> readLine() throws IOException {
        byte[] data = new byte[2048];
        int len = is.read(data);
        if (len > 0) {
            return Optional.of(new String(data, 0, len));
        } else {
            return Optional.empty();
        }
    }

}
