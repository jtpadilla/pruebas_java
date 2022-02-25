package jtpadilla.tls.socket.custom;

import javax.net.ssl.*;
import java.security.KeyStore;

public class SSLCustomServer {
   private SSLServerSocket serverSocket;

   public SSLCustomServer(int port) throws Exception {

      KeyStore keyStore = KeyStore.getInstance("JKS");
      keyStore.load(Util.getServerKeyJks(), "servpass".toCharArray());

      KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      kmf.init(keyStore, "servpass".toCharArray());

      KeyStore trustedStore = KeyStore.getInstance("JKS");
      trustedStore.load(Util.getServerTrustedCertsJks(), "servpass".toCharArray());

      TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init(trustedStore);

      SSLContext sc = SSLContext.getInstance("TLSv1.2");
      TrustManager[] trustManagers = tmf.getTrustManagers();
      KeyManager[] keyManagers = kmf.getKeyManagers();
      sc.init(keyManagers, trustManagers, null);

      SSLServerSocketFactory ssf = sc.getServerSocketFactory();
      serverSocket = (SSLServerSocket) ssf.createServerSocket(port);
      
   }

   public void start() {
      Util.startServerWorking(serverSocket);
   }

}
