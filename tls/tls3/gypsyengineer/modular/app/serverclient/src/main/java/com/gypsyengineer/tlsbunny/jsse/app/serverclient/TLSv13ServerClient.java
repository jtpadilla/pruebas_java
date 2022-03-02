package com.gypsyengineer.tlsbunny.jsse.app.serverclient;

import com.gypsyengineer.tlsbunny.jsse.lib.client.ClientLauncher;
import com.gypsyengineer.tlsbunny.jsse.lib.server.ServerListener;

public class TLSv13ServerClient {

    public static void main(String[] args) throws Exception {
        ServerListener.runListenerForever();
        ClientLauncher.runClientOneTime();
    }

}