package code2care;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class TSLv1_3Example {

    private static final String[] ENABLED_PROTOCOLS = new String[] {
            "TLSv1.3"
    };

    private static final String[] ENABLED_CLIPHER_SUITS = new String[] {
            "TLS_AES_128_GCM_SHA256"
    };
    private static final String host="code2care.org";
    private static final int port=443;
    private static SSLSocket sslSocket;

    public static void main(String... args) throws Exception {
        getSslSocket();
        sendRequest();
        readResponse();
    }

    public static void getSslSocket() throws IOException {

        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port);

        // Setting the TSLv1.3 protocol
        sslSocket.setEnabledProtocols(ENABLED_PROTOCOLS);

        // Setting the Cipher: TLS_AES_128_GCM_SHA256
        sslSocket.setEnabledCipherSuites(ENABLED_CLIPHER_SUITS);

        // Handshake
        sslSocket.startHandshake();

    }

    /**
     * Read the sslSocket response
     *
     */
    public static void readResponse() {

        try(BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()))) {

            String responseData;
            while ((responseData = in.readLine()) != null)
                System.out.println(responseData);

        } catch (IOException e) {
            System.out.println("Error occurred while reading Socket Response...");
            e.printStackTrace();
        }
    }

    public static void sendRequest() throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(sslSocket.getOutputStream());
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(outputStreamWriter));
        printWriter.println("GET /test-url HTTP/1.1\r\n");
        printWriter.flush();
    }

}