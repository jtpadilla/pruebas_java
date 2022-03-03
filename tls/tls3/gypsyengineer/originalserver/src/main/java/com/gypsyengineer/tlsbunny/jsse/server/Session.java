package com.gypsyengineer.tlsbunny.jsse.server;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Session implements Runnable {

    final private SSLSocket socket;
    final private InputStream is;
    final private OutputStream os;

    public Session(SSLSocket socket) throws IOException {
        this.socket = socket;
        is = new BufferedInputStream(socket.getInputStream());
        os = new BufferedOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            System.out.println("SESSION: Se inicia una nueva sesion.");
            Optional<String> message = readMessage();
            if (message.isEmpty()) {
                throw new IOException("No se han recibido datos");
            } else {
                writeMessage(message.get());
            }
        } catch (Exception e) {
            System.out.printf("SESSION: Se ha producido un error -> %s%n", e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
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

}
