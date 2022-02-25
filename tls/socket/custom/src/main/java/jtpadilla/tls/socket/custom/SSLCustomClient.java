package jtpadilla.tls.socket.custom;

import javax.net.ssl.*;
import java.security.KeyStore;

public class SSLCustomClient {

   private SSLSocket client;

   public SSLCustomClient(String address, int port) throws Exception {

      KeyStore keyStore = KeyStore.getInstance("JKS");
      keyStore.load(Util.getClientKeyJks(), "clientpass".toCharArray());

      KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      kmf.init(keyStore, "clientpass".toCharArray());

      KeyStore trustedStore = KeyStore.getInstance("JKS");
      trustedStore.load(Util.getCientTrustedCertsJks(), "clientpass".toCharArray());

      TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init(trustedStore);

      SSLContext sc = SSLContext.getInstance("TLSv1.2");
      TrustManager[] trustManagers = tmf.getTrustManagers();
      KeyManager[] keyManagers = kmf.getKeyManagers();
      sc.init(keyManagers, trustManagers, null);

      SSLSocketFactory ssf = sc.getSocketFactory();
      client = (SSLSocket) ssf.createSocket(address, port);
      client.startHandshake();
   }

   public void start() {
      Util.startClientWorking(client);
   }

}
