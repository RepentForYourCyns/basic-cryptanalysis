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
    public static String cipher(String in, int key, int encOrDec) {
        String out = "";
        for (int i = 0; i < in.length(); i++) {
            int asciiIn = Character.toUpperCase(in.charAt(i));
            int asciiOut;
            asciiIn -= 64;
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
            asciiOut += 64;
            char chOut = (char) asciiOut;
            out += chOut;
        }
        return out;
    }

    public static String tryAllKeys(String in) {
        ArrayList<String> outs = new ArrayList<>();
        for(int k = 1; k < 26; k++) {
            outs.add(cipher(in, k, DECIPHER));
        }
        int[] diffs = new int[outs.size()];
        for(int i = 0; i < diffs.length; i++) {
            diffs[i] = FrequencyAnalyser.letterFreqDeviation(outs.get(i));
        }
        int smallestDiff = Integer.MAX_VALUE;
        int smallestDiffIndex = 0;
        for(int i = 0; i < diffs.length; i++) {
            if(diffs[i] < smallestDiff) {
                smallestDiff = diffs[i];
                smallestDiffIndex = i;
            }
        }
        return outs.get(smallestDiffIndex);
    }

    // TODO Determine if output is likely to be English
}
