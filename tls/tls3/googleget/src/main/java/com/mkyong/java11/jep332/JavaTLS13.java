package com.mkyong.java11.jep332;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class JavaTLS13 {

    private static final String[] ENABLED_PROTOCOLS = new String[] {
            "TLSv1.3"
    };

    private static final String[] ENABLED_CLIPHER_SUITS = new String[] {
            "TLS_AES_128_GCM_SHA256"
    };

//    static final String[] ENABLED_PROTOCOLS = new String[] {
//            "TLSv1.2"
//    };
//
//    static final String[] ENABLED_CLIPHER_SUITS = new String[] {
//            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256"
//    };

    public static void main(String[] args) throws Exception {

        SSLSocket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = (SSLSocket) factory.createSocket("google.com", 443);

            socket.setEnabledProtocols(ENABLED_PROTOCOLS);
            socket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);

            socket.startHandshake();

            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            out.println("GET / HTTP/1.0");
            out.println();
            out.flush();

            if (out.checkError())
                System.out.println("SSLSocketClient:  java.io.PrintWriter error");

            /* read response */
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (socket != null)
                socket.close();
            if (out != null)
                out.close();
            if (in != null)
                in.close();
        }

    }

}
