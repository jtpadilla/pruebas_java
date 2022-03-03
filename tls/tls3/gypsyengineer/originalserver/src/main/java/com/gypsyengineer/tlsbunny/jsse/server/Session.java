package com.gypsyengineer.tlsbunny.jsse.server;

import javax.net.ssl.SSLSocket;
import java.io.*;

public class Session implements Runnable {

    final private SSLSocket socket;

    public Session(SSLSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            System.out.println("SESSION: Se inicia una nueva sesion.");

            InputStream is = new BufferedInputStream(socket.getInputStream());
            OutputStream os = new BufferedOutputStream(socket.getOutputStream());

            byte[] data = new byte[2048];
            int len = is.read(data);
            if (len <= 0) {
                throw new IOException("no data received");
            }
            System.out.printf("SESSION ======> Ha recibido %d bytes: %s%n", len, new String(data, 0, len));
            os.write(data, 0, len);
            os.flush();

        } catch (Exception e) {
            System.out.printf("SESSION: Se ha producido un error -> %s%n", e.getMessage());
        }

    }

}
