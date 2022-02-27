package com.baeldung.sasl;

import javax.security.auth.callback.*;
import javax.security.sasl.AuthorizeCallback;
import javax.security.sasl.RealmCallback;
import java.io.IOException;

public class ServerCallbackHandler implements CallbackHandler {

    @Override
    public void handle(Callback[] cbs) throws IOException, UnsupportedCallbackException {

        for (Callback cb : cbs) {
            if (cb instanceof AuthorizeCallback ac) {
                ac.setAuthorized(true);
            } else if (cb instanceof NameCallback nc) {
                nc.setName("username");

            } else if (cb instanceof PasswordCallback pc) {
                pc.setPassword("password".toCharArray());
            } else if (cb instanceof RealmCallback rc) {
                rc.setText("myServer");
            }
        }

    }
}