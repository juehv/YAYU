/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.juehvtech.yayu.util.encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Jens
 */
public class TextEncrypter {

    public static byte[] encrypt(char[] text) throws Exception {
        byte[] input = new String(text).getBytes();
        SecretKeySpec key = KeyLoader.loadDefaultKey();
        Cipher cipher = Cipher.getInstance("AES");
        System.out.println("input text : " + new String(input) + " bytes: " + input.length);

        // encryption pass

        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipher.update(input);
        ByteBuffer outBuf = ByteBuffer.wrap(cipher.doFinal());
        System.out.println("cipher text: " + new String(outBuf.array()) + " bytes: " + outBuf.array().length);

        return outBuf.array();
    }

    public static String encryptBase64(char[] text) throws Exception {
        byte[] crypt = TextEncrypter.encrypt(text);
        System.out.println("Base 64: " + Base64.encode(crypt));
        return Base64.encode(crypt);
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