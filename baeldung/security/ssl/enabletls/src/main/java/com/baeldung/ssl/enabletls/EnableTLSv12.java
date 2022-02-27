package com.baeldung.ssl.enabletls;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class EnableTLSv12 {

    Logger logger = Logger.getLogger(EnableTLSv12.class.getName());

    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {

        if (args.length != 2) {
            System.out.println("Provide the server url and the secure port:");
            System.exit(-1);
        }

        EnableTLSv12 enableTLSv12 = new EnableTLSv12(
                args[0],
                Integer.parseInt(Objects.requireNonNull(args[1]))
        );

//        enableTLSv12.enableTLSv12UsingSSLParameters();
//        enableTLSv12.enableTLSv12UsingProtocol();
        enableTLSv12.enableTLSv12UsingSSLContext();
//        enableTLSv12.enableTLSv12UsingHttpConnection();

    }

    final private String url;
    final private int port;

    public EnableTLSv12(String url, int port) {
        this.url = Objects.requireNonNull(url);
        this.port = port;
    }

    private void handleCommunication(SSLSocket socket, String usedTLSProcess) throws IOException {

        logger.info("Enabled TLS v1.2 on " + usedTLSProcess);

        try (   PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("GET / HTTP/1.0");
            out.println();
            out.flush();
            if (out.checkError()) {
                logger.severe("SSLSocketClient:  java.io.PrintWriter error");
                return;
            }

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                logger.info(inputLine);

        }

    }

    public void enableTLSv12UsingSSLParameters() throws UnknownHostException, IOException {

        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(url.trim(), port);

        // Asignandole SSLParameters al SSLSocket
        SSLParameters params = new SSLParameters();
        params.setProtocols(new String[] { "TLSv1.2" });
        sslSocket.setSSLParameters(params);

        sslSocket.startHandshake();
        handleCommunication(sslSocket, "SSLSocketFactory-SSLParameters");

    }

    public void enableTLSv12UsingProtocol() throws IOException {

        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(url, port);

        // Modificando el SSLSocket
        sslSocket.setEnabledProtocols(new String[] { "TLSv1.2" });

        sslSocket.startHandshake();
        handleCommunication(sslSocket, "SSLSocketFactory-EnabledProtocols");

    }

    public void enableTLSv12UsingSSLContext() throws NoSuchAlgorithmException, KeyManagementException, UnknownHostException, IOException {

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, new SecureRandom());

        SSLSocketFactory socketFactory = sslContext.getSocketFactory();

        SSLSocket socket = (SSLSocket) socketFactory.createSocket(url, port);

        handleCommunication(socket, "SSLContext");

    }

    public void enableTLSv12UsingHttpConnection() throws IOException, NoSuchAlgorithmException, KeyManagementException {

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, new SecureRandom());

        URL urls = new URL("https://" + url + ":" + port);
        HttpsURLConnection connection = (HttpsURLConnection) urls.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String input;
            while ((input = br.readLine()) != null) {
                logger.info(input);
            }
        }
        logger.info("Created TLSv1.2 connection on HttpsURLConnection");

    }

}
