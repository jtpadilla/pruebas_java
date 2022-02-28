package com.baeldung.ssl.example.client;

import javax.net.SocketFactory;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleClient {

    static final String[] ENABLED_CLIPHER_SUITS = new String[] { "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256" };
    static final String[] ENABLED_PROTOCOLS = new String[] { "TLSv1.2" };

    static String startClient(String host, int port) throws IOException {

        SocketFactory factory = SSLSocketFactory.getDefault();

        try (SSLSocket connection = (SSLSocket) factory.createSocket(host, port)) {

            connection.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);
            connection.setEnabledProtocols(ENABLED_PROTOCOLS);

            SSLParameters sslParams = new SSLParameters();
            sslParams.setEndpointIdentificationAlgorithm("HTTPS");
            connection.setSSLParameters(sslParams);

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return input.readLine();

        }

    }

    public static void main(String[] args) throws IOException {
        String currentPath = new java.io.File(".").getCanonicalPath();
        System.out.println("Current dir:" + currentPath);
        System.out.println(startClient("localhost", 8443));
    }

}
