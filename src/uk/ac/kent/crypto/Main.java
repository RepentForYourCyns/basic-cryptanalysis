package uk.ac.kent.crypto;

import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String USAGE = "\nUsage:\n\njava uk.ac.kent.crypto.Main [Arguments] [Options...]" + "\n\nArguments:"
            + "\n\t-c [caesar|...] \tWhich cipher to operate on" + "\n\nCaesar Cipher Arguments:"
            + "\n\t[-e|-d]\t\t\tWhether to encipher or decipher" + "\n\t-k <key>\t\tA number from 0 to 25 to act as the key"
            + "\n\t-t <text>\t\tThe text to encipher or decipher" + "\n\nOptions:" + "\n\t--help\t\t\tDisplays help";

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
        CipherType cipherType = null;

        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            if (i == 0) {
                if (s.equals("-c")) {
                    boolean cipherSet = false;
                    try {
                        String cipherTypeStr = args[++i];
                        switch (cipherTypeStr) {
                            case "caesar":
                                cipherType = cipherSet == false ? CipherType.Caesar : cipherType;
                                cipherSet = true;
                                break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        usage();
                    }
                    break;
                } else {
                    usage();
                }
            }
            boolean cipherSet = false;
            switch (s) {
                case "-e":
                    encOrDec = cipherSet ? encOrDec : CaesarCipher.ENCIPHER;
                    cipherSet = true;
                    break;
                case "-d":
                    encOrDec = cipherSet ? encOrDec : CaesarCipher.DECIPHER;
                    cipherSet = true;
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
                case "--help":
                    usage();
                    return;
                default:
                    usage();
                    return;
            }
        }

        List<Object> notNull = Arrays.asList(encOrDec, key, text, cipherType);
        if(notNull.contains(null)) {
            usage();
        }

        System.out.println(CaesarCipher.cipher(text, key, encOrDec));
    }

    private static void usage() {
        System.out.println(USAGE);
        System.exit(-1);
    }

    private enum CipherType {
        Caesar
    }
}
