package com.gypsyengineer.tlsbunny.jsse.server;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ServerSession implements Runnable {

    final private SSLSocket socket;
    final private InputStream is;
    final private OutputStream os;

    public ServerSession(SSLSocket socket) throws IOException {
        this.socket = socket;
        System.out.println("WantClientAuth:" + socket.getWantClientAuth());
        System.out.println("NeedClientAuth:" + socket.getNeedClientAuth());
        System.out.println("UseClientMode:" + socket.getUseClientMode());
        System.out.println("EnableSessionCreation:" + socket.getEnableSessionCreation());
        is = new BufferedInputStream(socket.getInputStream());
        os = new BufferedOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            System.out.println("SERVER-SESSION: Se inicia una nueva sesion.");
            Optional<String> messageOptional = readMessage();
            if (messageOptional.isEmpty()) {
                throw new IOException("No se han recibido datos");
            } else {
                String message = messageOptional.get();
                writeMessage(message);
                System.out.printf("SERVER-SESSION: Ha recibido %d bytes: %s%n", message.length(), message);
            }
        } catch (Exception e) {
            System.out.printf("SERVER-SESSION: Se ha producido un error -> %s%n", e.getMessage());
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
