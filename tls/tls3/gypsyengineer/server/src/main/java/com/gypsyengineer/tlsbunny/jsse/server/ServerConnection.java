package com.gypsyengineer.tlsbunny.jsse.server;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConnection implements Runnable {

    static private AtomicInteger sessionCounter = new AtomicInteger(0);

    final private SSLSocket socket;
    final private int sessionId;
    final private Logger logger;

    public ServerConnection(SSLSocket socket) {
        this.socket = socket;
        this.sessionId = sessionCounter.getAndIncrement();
        logger = Logger.getLogger(String.format("server.%d", this.sessionId));
    }

    @Override
    public void run() {

        logger.info("accepted");

        try {
            InputStream is = new BufferedInputStream(socket.getInputStream());
            OutputStream os = new BufferedOutputStream(socket.getOutputStream());

            byte[] data = new byte[2048];
            int len = is.read(data);
            if (len <= 0) {
                throw new IOException("no data received");
            }

            logger.info(String.format("server received %d bytes: %s%n", len, new String(data, 0, len)));
            os.write(data, 0, len);
            os.flush();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error durante el procesado de la conexion", e);

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error durante el cierre de la conexion", e);
                e.printStackTrace();
            }

        }
    }

}
