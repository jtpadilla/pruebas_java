package com.gypsyengineer.tlsbunny.jsse;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/*
 * Don't forget to set the following system properties when you run the class:
 *
 *     javax.net.ssl.keyStore
 *     javax.net.ssl.keyStorePassword
 *     javax.net.ssl.trustStore
 *     javax.net.ssl.trustStorePassword
 *
 * More details can be found in JSSE docs.
 *
 * For example:
 *
 *     java -cp classes \
 *         -Djavax.net.ssl.keyStore=keystore \
 *         -Djavax.net.ssl.keyStorePassword=passphrase \
 *         -Djavax.net.ssl.trustStore=keystore \
 *         -Djavax.net.ssl.trustStorePassword=passphrase \
 *             com.gypsyengineer.tlsbunny.jsse.TLSv13Test
 *
 * For testing purposes, you can download the keystore file from
 *
 *     https://github.com/openjdk/jdk/tree/master/test/jdk/javax/net/ssl/etc
 */
public class TLSv13Test {

    private static final int DELAY = 1000; // in millis

    private static final String[] ENABLED_PROTOCOLS = new String[] {
            "TLSv1.3"
    };

    private static final String[] ENABLED_CLIPHER_SUITS = new String[] {
            "TLS_AES_128_GCM_SHA256"
    };

    private static final String message = "Like most of life's problems, this one can be solved with bending!";

    public static void main(String[] args) throws Exception {

        try (EchoServer server = EchoServer.create()) {

            new Thread(server).start();
            Thread.sleep(DELAY);

            try (SSLSocket socket = createSocket("localhost", server.port())) {
                InputStream is = new BufferedInputStream(socket.getInputStream());
                OutputStream os = new BufferedOutputStream(socket.getOutputStream());
                os.write(message.getBytes());
                os.flush();
                byte[] data = new byte[2048];
                int len = is.read(data);
                if (len <= 0) {
                    throw new IOException("No se han recibido datos!");
                }
                System.out.printf("======[CLIENTE]> Ha recibido %d bytes: %s%n", len, new String(data, 0, len));
            } catch (Throwable t) {
                System.out.println("Se ha producido une excepcion en el cliente.");
                t.printStackTrace();
            }

        }

    }

    public static SSLSocket createSocket(String host, int port) throws IOException {
        SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);
        socket.setEnabledProtocols(ENABLED_PROTOCOLS);
        socket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
        return socket;
    }

    public static class EchoServer implements Runnable, AutoCloseable {

        private static final int FREE_PORT = 0;

        private final SSLServerSocket sslServerSocket;

        private EchoServer(SSLServerSocket sslServerSocket) {
            this.sslServerSocket = sslServerSocket;
        }

        public int port() {
            return sslServerSocket.getLocalPort();
        }

        @Override
        public void close() throws IOException {
            if (sslServerSocket != null && !sslServerSocket.isClosed()) {
                sslServerSocket.close();
            }
        }

        @Override
        public void run() {
            System.out.printf("server started on port %d%n", port());

            try (SSLSocket socket = (SSLSocket) sslServerSocket.accept()) {
                System.out.println("accepted");
                InputStream is = new BufferedInputStream(socket.getInputStream());
                OutputStream os = new BufferedOutputStream(socket.getOutputStream());
                byte[] data = new byte[2048];
                int len = is.read(data);
                if (len <= 0) {
                    throw new IOException("no data received");
                }
                System.out.printf("======[SERVIDOR]> Ha recibido %d bytes: %s%n", len, new String(data, 0, len));
                os.write(data, 0, len);
                os.flush();
            } catch (Exception e) {
                System.out.printf("exception: %s%n", e.getMessage());
            }

            System.out.println("server stopped");
        }

        public static EchoServer create() throws IOException {
            return create(FREE_PORT);
        }

        public static EchoServer create(int port) throws IOException {
            ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
            SSLServerSocket socket = (SSLServerSocket) serverSocketFactory.createServerSocket(port);
            socket.setEnabledProtocols(ENABLED_PROTOCOLS);
            socket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
            return new EchoServer(socket);
        }

    }

}