/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minersinstrument.cryptographic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author pkopychenko
 */
public class DESedeEncrypter {

    //private static final String algorithm = "PBEWithSHA1AndDESede";
//    private static byte[] keyBytes = new byte[] { 0x73, 0x2f, 0x2d, 0x33, (byte) 0xc8, 0x01, 0x73, 0x2b, 0x72,
//        0x06, 0x75, 0x6c, (byte) 0xbd, 0x44, (byte) 0xf9, (byte) 0xc1, (byte) 0xc1, 0x03,
//        (byte) 0xdd, (byte) 0xd9, 0x7c, 0x7c, (byte) 0xbe, (byte) 0x9e };
    /**
     * @param args the command line arguments
     */
    public static void main(String[] s) throws Exception {

        DESedeEncrypter encrypter = new DESedeEncrypter("key string", ALGORITM.SHA1);
        String OStr1 = "Привет!!! Какашки!!! 65535 65535 112";
        String SStr = encrypter.encrypt(OStr1);
        String OStr2 = encrypter.decrypt(SStr);
        System.out.println("Open String:" + OStr1 + "\nAfter encripting: " + SStr + "\nAfter decripting: " + OStr2);

        System.out.println("Тест 2");

        char[] c1 = OStr1.toCharArray();
        char[] c2 = encrypter.encrypt_c(c1);
        System.out.println(c2);
        char[] c3 = encrypter.decrypt_c(c2);
        System.out.println(c3);


    }
    private Cipher ecipher;
    private Cipher dcipher;

    //private final SecretKey sKey = KeyGenerator.getInstance("DESede").generateKey();
    public DESedeEncrypter() throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, Exception {

        SecretKey sKey = KeyGenerator.getInstance("DESede").generateKey();

        ecipher = Cipher.getInstance("DESede");
        dcipher = Cipher.getInstance("DESede");
        ecipher.init(Cipher.ENCRYPT_MODE, sKey);
        dcipher.init(Cipher.DECRYPT_MODE, sKey);
    }

    public enum ALGORITM {

        SHA1, MD5
    }

    public DESedeEncrypter(String keyString, ALGORITM alg) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, Exception {

        SecretKey sKey = null;

        switch (alg) {
            case SHA1:
                sKey = decodeKey(keyString, "SHA1");
                break;
            case MD5:
                sKey = decodeKey(keyString, "MD5");
                break;
        }

        ecipher = Cipher.getInstance("DESede");
        dcipher = Cipher.getInstance("DESede");
        ecipher.init(Cipher.ENCRYPT_MODE, sKey);
        dcipher.init(Cipher.DECRYPT_MODE, sKey);
    }

    private static SecretKey decodeKey(String keyString, String algoritm) throws Exception {
        byte[] keyBytes = keyBytes = java.util.Arrays.copyOfRange(Functions.computeDigestAsBytes(keyString, algoritm), 0, 24);

        //System.out.println(keyBytes.length);

        return new SecretKeySpec(keyBytes, "DESede");
    }

    public String encrypt(String str) throws UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] utf8 = str.getBytes("UTF8");
        byte[] enc = ecipher.doFinal(utf8);
        return bytesToHexString(enc);
    }

    public String decrypt(String str) throws IOException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] dec = hexToBytes(str);
        byte[] utf8 = dcipher.doFinal(dec);
        return new String(utf8, "UTF8");
    }

    public String encrypt(char[] c) throws UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] enc = ecipher.doFinal(new String(c).getBytes("UTF8"));
        return bytesToHexString(enc);
    }

    public char[] encrypt_c(char[] utf8) throws UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] enc = ecipher.doFinal(new String(utf8).getBytes("UTF8"));
        return bytesToHex(enc);
    }

    public char[] decrypt_c(char[] utf8) throws IOException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] dec = hexToBytes(utf8);
        byte[] ret = dcipher.doFinal(dec);
        return new String(ret, "UTF8").toCharArray();
    }
    private static final char kDigits[] = {'0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static char[] bytesToHex(byte abyte0[]) {
        int i = abyte0.length;
        char ac[] = new char[i * 2];
        for (int j = 0; j < i; j++) {
            int k = (abyte0[j] + 256) % 256;
            int l = k >> 4;
            int i1 = k & 0xf;
            ac[j * 2 + 0] = kDigits[l];
            ac[j * 2 + 1] = kDigits[i1];
        }

        return ac;
    }

    public static byte[] hexToBytes(char ac[]) {
        int i = ac.length / 2;
        byte abyte0[] = new byte[i];
        for (int j = 0; j < i; j++) {
            int k = Character.digit(ac[j * 2], 16);
            int l = Character.digit(ac[j * 2 + 1], 16);
            int i1 = k << 4 | l;
            if (i1 > 127) {
                i1 -= 256;
            }
            abyte0[j] = (byte) i1;
        }

        return abyte0;
    }

    public static byte[] hexToBytes(String s) {
        return hexToBytes(s.toCharArray());
    }

    public static String stringToHex(String s) {
        return new String(bytesToHex(s.getBytes()));
    }

    public static String bytesToHexString(byte abyte0[]) {
        return new String(bytesToHex(abyte0));
    }
}
