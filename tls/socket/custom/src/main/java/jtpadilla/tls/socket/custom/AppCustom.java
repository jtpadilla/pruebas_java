package jtpadilla.tls.socket.custom;

public class AppCustom {

   public static void main(String[] args) throws Exception {
      new SSLCustomServer(5557).start();
      new SSLCustomClient("localhost",5557).start();
   }

}
