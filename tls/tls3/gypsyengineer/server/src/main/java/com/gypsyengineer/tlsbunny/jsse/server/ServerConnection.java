package com.gypsyengineer.tlsbunny.jsse.server;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConnection implements Runnable {

    static private AtomicInteger sessionCounter = new AtomicInteger(0);

    final private SSLSocket socket;
    final private Logger logger;
    final private InputStream is;
    final private OutputStream os;

    public ServerConnection(SSLSocket socket) throws IOException {
        logger = Logger.getLogger(String.format("server.%d", sessionCounter.getAndIncrement()));
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

    @Override
    public void run() {

        logger.info("accepted");

        try {
            while(true) {
                Optional<String> line = readLine();
                if (line.isPresent()) {
                    logger.info(String.format("Rx: %s%n", line.get()));
                    writeLine(line.get());
                } else {
                    throw new IOException("No se han recibido datos!");
                }
            }

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
