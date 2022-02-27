package com.baeldung.cipher.test;

import com.baeldung.cipher.Encryptor;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static org.assertj.core.api.Assertions.assertThat;

public class EncryptorUnitTest {
    private String encKeyString;
    private String message;
    private String  certificateString;
    private Encryptor encryptor;

    @Before
    public void init() {
        encKeyString =  "1234567890123456";
        message = "This is a secret message";
        encryptor =  new Encryptor();
        certificateString = """
                -----BEGIN CERTIFICATE-----
                MIICVjCCAb8CAg37MA0GCSqGSIb3DQEBBQUAMIGbMQswCQYDVQQGEwJKUDEOMAwG
                A1UECBMFVG9reW8xEDAOBgNVBAcTB0NodW8ta3UxETAPBgNVBAoTCEZyYW5rNERE
                MRgwFgYDVQQLEw9XZWJDZXJ0IFN1cHBvcnQxGDAWBgNVBAMTD0ZyYW5rNEREIFdl
                YiBDQTEjMCEGCSqGSIb3DQEJARYUc3VwcG9ydEBmcmFuazRkZC5jb20wHhcNMTIw
                ODIyMDUyNzIzWhcNMTcwODIxMDUyNzIzWjBKMQswCQYDVQQGEwJKUDEOMAwGA1UE
                CAwFVG9reW8xETAPBgNVBAoMCEZyYW5rNEREMRgwFgYDVQQDDA93d3cuZXhhbXBs
                ZS5jb20wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMYBBrx5PlP0WNI/ZdzD
                +6Pktmurn+F2kQYbtc7XQh8/LTBvCo+P6iZoLEmUA9e7EXLRxgU1CVqeAi7QcAn9
                MwBlc8ksFJHB0rtf9pmf8Oza9E0Bynlq/4/Kb1x+d+AyhL7oK9tQwB24uHOueHi1
                C/iVv8CSWKiYe6hzN1txYe8rAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAASPdjigJ
                kXCqKWpnZ/Oc75EUcMi6HztaW8abUMlYXPIgkV2F7YanHOB7K4f7OOLjiz8DTPFf
                jC9UeuErhaA/zzWi8ewMTFZW/WshOrm3fNvcMrMLKtH534JKvcdMg6qIdjTFINIr
                evnAhf0cwULaebn+lMs8Pdl7y37+sfluVok=
                -----END CERTIFICATE-----""";
    }

    @Test
    public void givenEncryptionKey_whenMessageIsPassedToEncryptor_thenMessageIsEncrypted() throws Exception {

        byte[] encryptedMessage = encryptor.encryptMessage(
                message.getBytes(),
                encKeyString.getBytes()
        );

        assertThat(encryptedMessage).isNotNull();
        assertThat(encryptedMessage.length  % 32).isEqualTo(0);

    }

    @Test
    public void givenCertificateWithPublicKey_whenMessageIsPassedToEncryptor_thenMessageIsEncrypted() throws Exception {

        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        InputStream is = new ByteArrayInputStream(certificateString.getBytes());
        X509Certificate certificate = (X509Certificate) factory.generateCertificate(is);

        byte[] encryptedMessage = encryptor.encryptMessage(message.getBytes(), certificate);

        assertThat(encryptedMessage).isNotNull();
        assertThat(encryptedMessage.length  % 128).isEqualTo(0);

    }

    @Test
    public void givenEncryptionKey_whenMessageIsEncrypted_thenDecryptMessage() throws Exception{

        byte[] encryptedMessageBytes = encryptor.encryptMessage(
                message.getBytes(),
                encKeyString.getBytes()
        );

        byte[] clearMessageBytes = encryptor.decryptMessage(encryptedMessageBytes, encKeyString.getBytes());
        assertThat(message).isEqualTo(new String(clearMessageBytes));

    }

}
