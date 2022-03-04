package com.gypsyengineer.tlsbunny.jsse.app.server;

import com.gypsyengineer.tlsbunny.jsse.lib.server.ServerListener;

public class TLSv13Server {

    public static void main(String[] args) throws Exception {
        try (ServerListener server = new ServerListener()) {
            server.run();
        }
    }

}