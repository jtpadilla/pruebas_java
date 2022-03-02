package com.gypsyengineer.tlsbunny.jsse.client;

import java.util.Optional;

public class ClientLauncher {

    private static final String SERVER = "localhost";
    private static final int PORT = 4443;

    private static final String message = "Like most of life's problems, this one can be solved with bending!";

    public static void runClientOneTime() throws Exception {
        try (ClientSession clientSession = new ClientSession(SERVER, PORT)) {
            clientSession.writeLine(message);
            Optional<String> responde = clientSession.readLine();
            boolean ok = responde.isPresent() && responde.get().equals(message);
            System.out.println(ok  ? "OK" : "Error");
        }
    }

}
