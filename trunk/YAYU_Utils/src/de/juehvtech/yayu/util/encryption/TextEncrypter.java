/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.util.encryption;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Jens
 */
public class TextEncrypter {

    public static byte[] encrypt(char[] text) throws Exception {
        byte[] input = new String(text).getBytes();
        SecretKeySpec key = KeyLoader.loadDefaultKey();
        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipher.update(input);
        ByteBuffer outBuf = ByteBuffer.wrap(cipher.doFinal());

        return outBuf.array();
    }

    public static String encryptBase64(char[] text) throws Exception {
        byte[] crypt = TextEncrypter.encrypt(text);
        return DatatypeConverter.printBase64Binary(crypt);
    }

    public static String encryptBase64IgnoreError(char[] text) {
        try {
            return encryptBase64(text);
        } catch (Exception ex) {
            Logger.getLogger(TextEncrypter.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
