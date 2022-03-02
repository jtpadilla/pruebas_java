package com.gypsyengineer.tlsbunny.jsse.app.serverclient;

import com.gypsyengineer.tlsbunny.jsse.lib.client.ClientLauncher;
import com.gypsyengineer.tlsbunny.jsse.lib.server.ServerListener;

import java.util.concurrent.TimeUnit;

public class TLSv13ServerClient {

    public static void main(String[] args) throws Exception {
        new Thread(ServerListener::runListenerForever).start();
        TimeUnit.SECONDS.sleep(2);
        ClientLauncher.runClientOneTime();
    }

}