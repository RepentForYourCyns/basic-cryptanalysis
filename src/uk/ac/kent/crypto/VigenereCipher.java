package uk.ac.kent.crypto;

public class VigenereCipher {
    public static final int ENCIPHER = 0;
    public static final int DECIPHER = 1;

    public static String rotate(String in, String keyStr, int encOrDec) {
        int[] key = new int[keyStr.length()];
        for (int i = 0; i < key.length; i++) {
            key[i] = Character.toUpperCase(keyStr.charAt(i)) - 'A';
        }
        String out = "";
        for (int i = 0; i < in.length(); i++) {
            out += CaesarCipher.rotateLetter(Character.toUpperCase(in.charAt(i)), key[i % key.length], encOrDec);
        }
        return out;
    }

    public static String autoDecipher(String cipher) {
        // TODO Attempt to infer key length by looking for repetitions
        String[] outs = new String[cipher.length()];
        for (int i = 0; i < outs.length; i++) {
            outs[i] = autoDecipher(cipher, i + 1);
        }
        return FrequencyAnalyser.pickLikelyPlaintext(outs);
    }

    public static String autoDecipher(String cipher, int keyLength) {
        String[] subCiphers = new String[keyLength];
        for (int i = 0; i < subCiphers.length; i++) {
            subCiphers[i] = "";
        }
        for (int i = 0; i < cipher.length(); i++) {
            subCiphers[i % keyLength] += Character.toUpperCase(cipher.charAt(i));
        }
        int[] keyInts = new int[keyLength];
        for (int i = 0; i < keyInts.length; i++) {
            keyInts[i] = FrequencyAnalyser.pickLikelyKey(CaesarCipher.tryAllKeys(subCiphers[i])) + 'A';
        }
        char[] key = new char[keyInts.length];
        for (int i = 0; i < key.length; i++) {
            key[i] = (char) keyInts[i];
        }
        return rotate(cipher, String.valueOf(key), DECIPHER);
    }
}
