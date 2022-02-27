package com.baeldung.keystore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public class JavaKeyStore {

    final private String keyStoreName;
    final private char[] keyStorePassword;

    private KeyStore keyStore;

    private String keyStoreType;

    public JavaKeyStore(String keyStoreType, String keyStorePassword, String keyStoreName) {
        this.keyStoreName = keyStoreName;
        this.keyStoreType = keyStoreType;
        this.keyStorePassword = keyStorePassword.toCharArray();
    }

    public void createEmptyKeyStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {

        if (keyStoreType == null || keyStoreType.isEmpty()) {
            keyStoreType = KeyStore.getDefaultType();
        }

        keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, keyStorePassword);

        // Save the keyStore
        FileOutputStream fos = new FileOutputStream(keyStoreName);
        keyStore.store(fos, keyStorePassword);
        fos.close();

    }

    public void loadKeyStore() throws IOException, CertificateException, NoSuchAlgorithmException {
        keyStore.load(new FileInputStream(keyStoreName), keyStorePassword);
    }

    public void setEntry(String alias, KeyStore.SecretKeyEntry secretKeyEntry, KeyStore.ProtectionParameter protectionParameter) throws KeyStoreException {
        keyStore.setEntry(alias, secretKeyEntry, protectionParameter);
    }

    public KeyStore.Entry getEntry(String alias) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(keyStorePassword);
        return keyStore.getEntry(alias, protParam);
    }

    public void setKeyEntry(String alias, PrivateKey privateKey, String keyPassword, Certificate[] certificateChain) throws KeyStoreException {
        keyStore.setKeyEntry(alias, privateKey, keyPassword.toCharArray(), certificateChain);
    }

    public void setCertificateEntry(String alias, Certificate certificate) throws KeyStoreException {
        keyStore.setCertificateEntry(alias, certificate);
    }

    public Certificate getCertificate(String alias) throws KeyStoreException {
        return keyStore.getCertificate(alias);
    }

    public void deleteEntry(String alias) throws KeyStoreException {
        keyStore.deleteEntry(alias);
    }

    public void deleteKeyStore() throws KeyStoreException, IOException {
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            keyStore.deleteEntry(alias);
        }
        keyStore = null;
        Files.delete(Paths.get(keyStoreName));
    }

    public KeyStore getKeyStore() {
        return this.keyStore;
    }

}
