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
public class TextDecrypter {

    public static char[] decrypt(byte[] text) throws Exception {
        SecretKeySpec key = KeyLoader.loadDefaultKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        ByteBuffer inBuf = ByteBuffer.wrap(text);

        cipher.update(inBuf.array());
        ByteBuffer outBuf = ByteBuffer.wrap(cipher.doFinal());
        return new String(outBuf.array()).toCharArray();
    }

    public static char[] decryptBase64(String text) throws Exception {
        return TextDecrypter.decrypt(DatatypeConverter.parseBase64Binary(text));
    }

    public static char[] decryptBase64IgnoreError(String text) {
        try {
            return TextDecrypter.decryptBase64(text);
        } catch (Exception ex) {
            Logger.getLogger(TextDecrypter.class.getName()).log(Level.SEVERE, null, ex);
            return "".toCharArray();
        }
    }
}
