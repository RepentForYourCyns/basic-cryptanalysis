package uk.ac.kent.crypto;

import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String USAGE = "\nUsage:\n\njava uk.ac.kent.crypto.Main -c <function> [arguments] [options...]"
            + "\n\nFunctions:"
            + "\n\tcaesar\t- Caesar Cipher"
            + "\n\tfreq\t- Frequency Analysis"
            + "\n\nFrequency Analysis Arguments:"
            + "\n\t-sm <text>\t\tStrict match the character frequencies in the provided text to the known character frequencies in English"
            + "\n\nCaesar Cipher Arguments:"
            + "\n\t[-e|-d]\t\t\tWhether to encipher or decipher"
            + "\n\t-k <key>\t\tA number from 0 to 25 to act as the key"
            + "\n\t-t <text>\t\tThe text to encipher or decipher"
            + "\n\tOR"
            + "\n\t--auto <text>\t\tAttempt to automatically find the key"
            + "\n\nOptions:"
            + "\n\t--help\t\t\tDisplays help";

    /**
     * Main method for running the programs
     * 
     * @param args Arguments to the program:
     *             [-e|-d] Whether to encrypt or decrypt
     *             -k <key> A number from 0 to 25
     *             -t <text> Text to be encrypted or decrypted
     */
    public static void main(String[] args) {
        Integer encOrDec = null;
        Integer key = null;
        String text = null;
        Function function = null;
        boolean encOrDecSet = false;

        // TODO Parse arguments using recursive descent (or something better than this)
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            if (i == 0) {
                if (s.equals("-c")) {
                    try {
                        String cipherTypeStr = args[++i];
                        switch (cipherTypeStr) {
                            case "caesar":
                                function = Function.CaesarCipher;
                                break;
                            case "freq":
                                function = Function.FrequencyAnalyser;
                                break;
                            default:
                                usage();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        usage();
                    }
                } else {
                    usage();
                }
            } else {
                switch (function) {
                    case CaesarCipher:
                        switch (s) {
                            case "-e":
                                encOrDec = encOrDecSet ? encOrDec : CaesarCipher.ENCIPHER;
                                encOrDecSet = true;
                                break;
                            case "-d":
                                encOrDec = encOrDecSet ? encOrDec : CaesarCipher.DECIPHER;
                                encOrDecSet = true;
                                break;
                            case "-k":
                                try {
                                    key = key != null ? null : Integer.parseInt(args[++i]);
                                    break;
                                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                    usage();
                                }
                            case "-t":
                                try {
                                    text = text != null ? null : args[++i];
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    usage();
                                }
                                break;
                            case "--auto":
                                try {
                                    System.out.println(CaesarCipher.tryAllKeys(args[++i]));
                                    System.exit(0);
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    usage();
                                }
                                break;
                            case "--help":
                                usage();
                            default:
                                usage();
                        }
                        break;
                    case FrequencyAnalyser:
                        switch (s) {
                            case "-sm":
                                try {
                                    System.out.println(FrequencyAnalyser.letterFreqDeviation(args[++i]));
                                    System.exit(0);
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    usage();
                                }
                                break;
                            default:
                                usage();
                        }
                        break;
                    default:
                        usage();
                }

            }
        }

        if(function.equals(Function.CaesarCipher)) {
            List<Object> notNull = Arrays.asList(encOrDec, key, text, function);
            if (notNull.contains(null)) {
                usage();
            }
    
            System.out.println(CaesarCipher.cipher(text, key, encOrDec));
        }
    }

    /**
     * Print usage information and exit
     */
    private static void usage() {
        System.out.println(USAGE);
        System.exit(-1);
    }

    /**
     * Enumerate the different types of ciphers the program deals with
     */
    private enum Function {
        CaesarCipher, FrequencyAnalyser
    }
}
