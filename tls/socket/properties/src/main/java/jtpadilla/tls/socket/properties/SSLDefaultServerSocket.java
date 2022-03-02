package jtpadilla.tls.socket.properties;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;

public class SSLDefaultServerSocket {

   private ServerSocket serverSocket;

   public SSLDefaultServerSocket(int port) throws IOException {
      SSLServerSocketFactory serverFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
      serverSocket = serverFactory.createServerSocket(port);
   }

   public void start() {
      Util.startServerWorking(serverSocket);
   }

}
