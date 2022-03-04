package com.gypsyengineer.tlsbunny.jsse.lib.server;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerSession implements Runnable {

    static private AtomicInteger sessionCounter = new AtomicInteger(0);

    final private SSLSocket socket;
    final private InputStream is;
    final private OutputStream os;

    public ServerSession(SSLSocket socket) throws IOException {
        this.socket = socket;
        this.is = new BufferedInputStream(socket.getInputStream());
        this.os = new BufferedOutputStream(socket.getOutputStream());
    }

    private Optional<String> readLine() throws IOException {
        byte[] data = new byte[2048];
        int len = is.read(data);
        if (len <= 0) {
            return Optional.empty();
        } else {
            return Optional.of(new String(data, 0, len));
        }
    }

    private void writeLine(String line) throws IOException {
        byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
        os.write(bytes, 0, bytes.length);
        os.flush();
    }

    private void echo() throws IOException {
        Optional<String> line = readLine();
        if (line.isPresent()) {
            writeLine(line.get());
        } else {
            throw new IOException("No se han recibido datos!");
        }
    }

    @Override
    public void run() {

        try {
            echo();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
