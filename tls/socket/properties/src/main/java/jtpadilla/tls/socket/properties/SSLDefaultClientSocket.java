package jtpadilla.tls.socket.properties;

import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

public class SSLDefaultClientSocket {

   Socket client = null;

   public SSLDefaultClientSocket(String server, int port) throws IOException {
      SSLSocketFactory clientFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      client = clientFactory.createSocket(server, port);
   }

   public void start() {
      Util.startClientWorking(client);
   }
}
