package uk.ac.kent.crypto;

public class VigenereCipher {
    public static final int ENCIPHER = 0;
    public static final int DECIPHER = 1;

    public static String rotate(String in, String keyStr, int encOrDec) {
        int[] key = new int[keyStr.length()];
        for(int i = 0; i < key.length; i++) {
            key[i] = Character.toUpperCase(keyStr.charAt(i)) - 65;
        }
        String out = "";
        for(int i = 0; i < in.length(); i++) {
            out += CaesarCipher.rotateLetter(Character.toUpperCase(in.charAt(i)), key[i % key.length], encOrDec);
        }
        return out;
    }

    public static String autoDecipher(String cipher) {
        return null;
    }

    public static String autoDecipher(String cipher, int keyLength) {
        return null;
    }
}
