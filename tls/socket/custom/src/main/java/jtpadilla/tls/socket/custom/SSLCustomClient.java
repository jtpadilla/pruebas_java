package jtpadilla.tls.socket.custom;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class SSLCustomClient {

   private SSLSocket client;

   public SSLCustomClient(String address, int port) throws Exception {

      KeyManagerFactory kmf = getKeyManagerFactory();
      TrustManagerFactory tmf = getTrustManagerFactory();

      SSLContext sc = getSSLContext(kmf, tmf);

      SSLSocketFactory ssf = sc.getSocketFactory();

      client = (SSLSocket) ssf.createSocket(address, port);
      client.startHandshake();

   }

   private SSLContext getSSLContext(KeyManagerFactory kmf, TrustManagerFactory tmf) throws NoSuchAlgorithmException, KeyManagementException {

      SSLContext sc = SSLContext.getInstance("TLSv1.2");

      TrustManager[] trustManagers = tmf.getTrustManagers();
      KeyManager[] keyManagers = kmf.getKeyManagers();

      sc.init(keyManagers, trustManagers, new SecureRandom());
      return sc;

   }

   private KeyManagerFactory getKeyManagerFactory() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {

      KeyStore keyStore = KeyStore.getInstance("JKS");
      keyStore.load(Util.getClientKeyJks(), "clientpass".toCharArray());

      KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      kmf.init(keyStore, "clientpass".toCharArray());
      return kmf;

   }

   private TrustManagerFactory getTrustManagerFactory() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {

      KeyStore trustedStore = KeyStore.getInstance("JKS");
      trustedStore.load(Util.getCientTrustedCertsJks(), "clientpass".toCharArray());

      TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init(trustedStore);
      return tmf;

   }

   public void start() {
      Util.startClientWorking(client);
   }

}
