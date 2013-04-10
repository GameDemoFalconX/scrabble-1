package server.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public class PasswordHasher {

    private final static int ITERATION_NUMBER = 1000;
    
    public PasswordHasher() {}

    public String MakePassword(String password, byte[] salt) {
        try {
            // Digest computation
            byte[] bDigest = getHash(ITERATION_NUMBER, password, salt);
            String sDigest = byteToBase64(bDigest);
            return sDigest;
        } catch (NoSuchAlgorithmException e) {}
        return null;
    }

    /**
     * From a password, a number of iterations and a salt, returns the
     * corresponding digest
     * @param iterationNb int The number of iterations of the algorithm
     * @param password String The password to encrypt
     * @param salt byte[] The salt
     * @return byte[] The digested password
     * @throws NoSuchAlgorithmException If the algorithm doesn't exist
     */
    public byte[] getHash(int iterationNb, String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(salt);
        try {
            byte[] input = digest.digest(password.getBytes("UTF-8"));
            for (int i = 0; i < iterationNb; i++) {
                digest.reset();
                input = digest.digest(input);
            }
            return input;
        } catch (UnsupportedEncodingException e) {}
        return null;
    }

    

    /**
     * From a base 64 representation, returns the corresponding byte[]
     * @param data String The base64 representation
     * @return byte[]
     * @throws IOException
     */
    public byte[] base64ToByte(String data) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(data);
    }

    /**
     * From a byte[] returns a base 64 representation
     * @param data byte[]
     * @return String
     * @throws IOException
     */
    public String byteToBase64(byte[] data) {
        BASE64Encoder endecoder = new BASE64Encoder();
        return endecoder.encode(data);
    }
}