package jtpadilla.tls.socket.custom;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Util {

   static public InputStream getClientKeyJks() {
      return getResource("clientKey.jks");
   }

   static public InputStream getCientTrustedCertsJks() {
      return getResource("clientTrustedCerts.jks");
   }

   static public InputStream getServerKeyJks() {
      return getResource("serverKey.jks");
   }

   static public InputStream getServerTrustedCertsJks() {
      return getResource("serverTrustedCerts.jks");
   }

   static public InputStream getResource(String resourceName) {
      return Util.class.getClassLoader().getResourceAsStream(resourceName);
   }

   public static void startClientWorking(final Socket client) {
      System.out.println("client start");
      new Thread() {
         public void run() {
            try {
               PrintWriter output = new PrintWriter(client.getOutputStream());
               output.println("Federico");
               output.flush();
               System.out.println("Federico sent");
               BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
               String received = input.readLine();
               System.out.println("Received : " + received);
               client.close();
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }.start();
   }
   
   public static void startServerWorking(final ServerSocket serverSocket) {
      System.out.println("server start");
      new Thread() {
         public void run() {
            try {
               Socket aClient = serverSocket.accept();
               System.out.println("client accepted");
               aClient.setSoLinger(true, 1000);
               BufferedReader input = new BufferedReader(new InputStreamReader(aClient.getInputStream()));
               String recibido = input.readLine();
               System.out.println("Recibido " + recibido);
               PrintWriter output = new PrintWriter(aClient.getOutputStream());
               output.println("Hello, " + recibido);
               output.flush();
               aClient.close();
            } catch (Exception e) {
               e.printStackTrace();
            }

         }
      }.start();
   }

}
