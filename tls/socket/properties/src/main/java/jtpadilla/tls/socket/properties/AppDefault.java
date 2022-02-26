package jtpadilla.tls.socket.properties;

import java.io.IOException;

public class AppDefault {

   public static void main(String[] args) throws IOException {

      // Servidor
      System.setProperty("javax.net.ssl.keyStore", "certs/serverKey.jks");
      System.setProperty("javax.net.ssl.keyStorePassword","serverpass");

      System.setProperty("javax.net.ssl.trustStore", "certs/serverTrustedCerts.jks");
      System.setProperty("javax.net.ssl.trustStorePassword", "serverpass");

      new SSLDefaultServerSocket(5557).start();

      // CLiente
      System.setProperty("javax.net.ssl.keyStore", "certs/clientKey.jks");
      System.setProperty("javax.net.ssl.keyStorePassword","clientpass");

      System.setProperty("javax.net.ssl.trustStore", "certs/clientTrustedCerts.jks");
      System.setProperty("javax.net.ssl.trustStorePassword", "clientpass");

      new SSLDefaultClientSocket("localhost",5557).start();

   }

}
