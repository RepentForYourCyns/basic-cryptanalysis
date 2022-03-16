package uk.ac.kent.crypto;

/**
 * Represents the caesar cipher
 */
public class CaesarCipher {
    public static final int ENCIPHER = 0;
    public static final int DECIPHER = 1;

    /**
     * Perform a caesar cipher/decipher
     * 
     * @param in       input text
     * @param key      key (a number from 1 to 25)
     * @param encOrDec whether to cipher or decipher the input text (use
     *                 CaesarCipher constants)
     * @return ciphered/deciphered text
     */
    public static String rotate(String in, int key, int encOrDec) {
        String out = "";
        for (int i = 0; i < in.length(); i++) {
            out += rotateLetter(Character.toUpperCase(in.charAt(i)), key, encOrDec);
        }
        return out;
    }

    /**
     * Rotate a string forwards or backwards in the alphabet
     * 
     * @param in       string to be rotated
     * @param key      number of steps to rotate by
     * @param encOrDec whether to rotate forwards (encipher) or backwards (decipher)
     * @return rotated string
     */
    public static char rotateLetter(char in, int key, int encOrDec) {
        int asciiOut;
        in -= 65;
        switch (encOrDec) {
            case ENCIPHER:
                asciiOut = (in + key) % 26;
                break;
            case DECIPHER:
                asciiOut = (in - key) % 26;
                if (asciiOut < 0) {
                    asciiOut += 26;
                }
                break;
            default:
                asciiOut = in;
        }
        return (char) (asciiOut + 65);
    }

    /**
     * Try all 25 possible keys on an input string
     * 
     * @param in string to decipher
     * @return an array containing all 25 possible deciphered strings, with each in
     *         the index corresponding to its key
     */
    public static String[] tryAllKeys(String in) {
        String[] outs = new String[26];
        for (int k = 1; k < 26; k++) {
            outs[k] = rotate(in, k, DECIPHER);
        }
        return outs;
    }
}
