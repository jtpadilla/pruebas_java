package com.gypsyengineer.tlsbunny.jsse.server;

public class TLSv13TestServer {

    public static void main(String[] args) throws Exception {
        try (Listener server = new Listener()) {
            server.run();
        }
    }

}