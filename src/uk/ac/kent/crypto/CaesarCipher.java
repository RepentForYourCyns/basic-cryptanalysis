package uk.ac.kent.crypto;

import java.util.ArrayList;

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
     * @param key key (a number from 1 to 25)
     * @param encOrDec whether to cipher or decipher the input text (use CaesarCipher constants)
     * @return ciphered/deciphered text
     */
    public static String rotate(String in, int key, int encOrDec) {
        String out = "";
        for (int i = 0; i < in.length(); i++) {
            int asciiIn = Character.toUpperCase(in.charAt(i));
            int asciiOut;
            asciiIn -= 65;
            switch (encOrDec) {
                case ENCIPHER:
                    asciiOut = (asciiIn + key) % 26;
                    break;
                case DECIPHER:
                    asciiOut = (asciiIn - key) % 26;
                    if(asciiOut < 0) {
                        asciiOut += 26;
                    }
                    break;
                default:
                    asciiOut = asciiIn;
            }
            asciiOut += 65;
            char chOut = (char) asciiOut;
            out += chOut;
        }
        return out;
    }

    public static ArrayList<String> autoDecipher(String in) {
        ArrayList<String> outs = new ArrayList<>();
        for(int k = 1; k < 26; k++) {
            outs.add(rotate(in, k, DECIPHER));
        }
        
        return outs;
    }
}
