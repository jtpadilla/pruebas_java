package com.gypsyengineer.tlsbunny.jsse.server;

public class TLSv13TestServer {

    public static void main(String[] args) throws Exception {
        try (ServerListener server = new ServerListener()) {
            server.run();
        }
    }

}