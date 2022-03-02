package com.gypsyengineer.tlsbunny.jsse.client;

import java.util.Optional;

public class TLSv13Client {

    private static final String SERVER = "localhost";
    private static final int PORT = 4443;

    private static final String message = "Like most of life's problems, this one can be solved with bending!";

    public static void main(String[] args) throws Exception {
        runClient();
    }

    private static void runClient() throws Exception {
        try (ClientSession clientSession = new ClientSession(SERVER, PORT)) {
            clientSession.writeLine(message);
            Optional<String> responde = clientSession.readLine();
            System.out.println(responde.isPresent() ? "OK" : "Error");
        }
    }

}