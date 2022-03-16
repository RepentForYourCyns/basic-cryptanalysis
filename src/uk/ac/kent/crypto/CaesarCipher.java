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
            out += rotateLetter(Character.toUpperCase(in.charAt(i)), key, encOrDec);
        }
        return out;
    }

    public static char rotateLetter(char in, int key, int encOrDec) {
        int asciiOut;
        in -= 65;
        switch (encOrDec) {
            case ENCIPHER:
                asciiOut = (in + key) % 26;
                break;
            case DECIPHER:
                asciiOut = (in - key) % 26;
                if(asciiOut < 0) {
                    asciiOut += 26;
                }
                break;
            default:
                asciiOut = in;
        }
        return (char) (asciiOut + 65);
    }

    public static ArrayList<String> autoDecipher(String in) {
        ArrayList<String> outs = new ArrayList<>();
        for(int k = 1; k < 26; k++) {
            outs.add(rotate(in, k, DECIPHER));
        }
        
        return outs;
    }
}
