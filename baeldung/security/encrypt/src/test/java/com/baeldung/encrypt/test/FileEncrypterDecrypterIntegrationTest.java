package com.baeldung.encrypt.test;

import com.baeldung.encrypt.FileEncrypterDecrypter;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileEncrypterDecrypterIntegrationTest {

    @Test
    public void givenStringAndFilename_whenEncryptingIntoFile_andDecryptingFileAgain_thenOriginalStringIsReturned() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        String originalContent = "foobar";
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();

        // Se crea la utilidad crifradora con la clave secreta e indicando el algoritmo
        FileEncrypterDecrypter fileEncrypterDecrypter = new FileEncrypterDecrypter(secretKey, "AES/CBC/PKCS5Padding");

        // Mediante la utilidad cifradora se genera un fichero al cual se le colocan los datos cifrados
        fileEncrypterDecrypter.encrypt(originalContent, "baz.enc");

        // Mediante la utilidad descifradora so se descifran los datos del fichero.
        String decryptedContent = fileEncrypterDecrypter.decrypt("baz.enc");

        // test
        assertThat(decryptedContent, is(originalContent));

        // Limpieza del fichero
        //noinspection ResultOfMethodCallIgnored
        new File("baz.enc").delete();

    }
}
