/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.cryptographic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PKopychenko
 */
public class Functions {
    //private static MessageDigest currentAlgorithm;


    /* Вычисление дайджеста сообщения для байтового массива о отображение его
     * в возвращаемом значении.
     * b - есть байтовый массив, для которого вычисляется дайджест.*/
    public static String computeDigest(byte[] b, String cr) {
        try {

            MessageDigest currentAlgorithm = MessageDigest.getInstance(cr);
            currentAlgorithm.reset();
            currentAlgorithm.update(b);

            byte[] hash = currentAlgorithm.digest();
            String d = "";

            int i;
            for (i = 0; i < hash.length; i++) {

                int v = hash[i] & 0xFF;
                if (v < 16) {
                    d += "0";
                }
                //d += Integer.toString( v ,  16 ).toUpperCase() + " ";
                d += Integer.toString(v, 16).toUpperCase();
            }
            return d;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    // Перегрузка

    public static String computeDigest(char[] c, String cr) {
        try {

            byte[] b = new byte[c.length];

            for (int i = 0; i < c.length; ++i) {
                b[i] = (byte) c[i];
            }
            MessageDigest currentAlgorithm = MessageDigest.getInstance(cr);
            currentAlgorithm.reset();
            currentAlgorithm.update(b);

            byte[] hash = currentAlgorithm.digest();
            String d = "";

            for (byte aHash : hash) {
                int v = aHash & 0xFF;
                if (v < 16) {
                    d += "0";
                }

                d += Integer.toString(v, 16).toUpperCase();
            }
            return d;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    // Перегрузка

    public static String computeDigest(String s, String cr) {
        try {

            byte[] b = new byte[s.length()];

            for (int i = 0; i < s.length(); ++i) {
                b[i] = (byte) s.charAt(i);
            }
            MessageDigest currentAlgorithm = MessageDigest.getInstance(cr);
            currentAlgorithm.reset();
            currentAlgorithm.update(b);

            byte[] hash = currentAlgorithm.digest();
            String d = "";

            for (byte aHash : hash) {
                int v = aHash & 0xFF;
                if (v < 16) {
                    d += "0";
                }
                //d += Integer.toString( v ,  16 ).toUpperCase() + " ";
                d += Integer.toString(v, 16).toUpperCase();
            }
            return d;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static byte[] computeDigestAsBytes(String s, String cr) throws NoSuchAlgorithmException {

        byte[] b = new byte[s.length()];

        for (int i = 0; i < s.length(); ++i) {
            b[i] = (byte) s.charAt(i);
        }
        MessageDigest currentAlgorithm = MessageDigest.getInstance(cr);
        currentAlgorithm.reset();
        currentAlgorithm.update(b);

        byte[] hash = currentAlgorithm.digest();

        return hash;

    }
}
