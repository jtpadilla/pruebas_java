package com.gypsyengineer.tlsbunny.jsse.client;

import java.io.*;

public class TLSv13TestClient {

    private static final String MESSAGE = "Like most of life's problems, this one can be solved with bending!";

    public static void main(String[] args) throws Exception {

        try (ClientSession clientSession = new ClientSession("localhost", 4443)) {
            clientSession.sendMessage(MESSAGE);
        }

    }

}