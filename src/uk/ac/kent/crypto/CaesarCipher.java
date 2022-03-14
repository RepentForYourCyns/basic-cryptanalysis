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
     * @param in input text
     * @param key key (a number from 0 to 25)
     * @param encOrDec whether to cipher or decipher the input text (use CaesarCipher constants)
     * @return ciphered/deciphered text
     */
    public static String cipher(String in, int key, int encOrDec) {
        String out = "";
        for (int i = 0; i < in.length(); i++) {
            int asciiIn = in.charAt(i);
            int asciiOut;
            switch (encOrDec) {
                case ENCIPHER:
                    asciiOut = asciiIn + key;
                    break;
                case DECIPHER:
                    asciiOut = asciiIn - key;
                    break;
                default:
                    asciiOut = asciiIn;
            }
            char chOut = (char) asciiOut;
            out += chOut;
        }
        return out;
    }

    // TODO Determine if output is likely to be English
}
