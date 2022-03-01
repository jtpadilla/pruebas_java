package com.baeldung.sasl;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.sasl.RealmCallback;

public class ClientCallbackHandler implements CallbackHandler {

    @Override
    public void handle(Callback[] cbs) {
        for (Callback cb : cbs) {
            if (cb instanceof NameCallback nc) {
                nc.setName("username");
            } else if (cb instanceof PasswordCallback pc) {
                pc.setPassword("password".toCharArray());
            } else if (cb instanceof RealmCallback rc) {
                rc.setText("myServer");
            }
        }
    }

}
