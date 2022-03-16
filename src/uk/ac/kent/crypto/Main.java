package uk.ac.kent.crypto;

public class Main {
    private static final String FUNC = "-f";
    private static final String CAESAR = "caesar";
    private static final String FREQ_ANALYSIS = "freq";
    private static final String LETTER_FREQ_MATCH = "-sm";
    private static final String CAESAR_ENC = "-e";
    private static final String CAESAR_DEC = "-d";
    private static final String CAESAR_KEY = "-k";
    private static final String CAESAR_TXT = "-t";
    private static final String CAESAR_AUTO = "-a";
    private static final String HELP1 = "--help";
    private static final String HELP2 = "-?";
    private static final String USAGE = "\nUsage:\n\njava uk.ac.kent.crypto.Main " + FUNC
            + " <function> [arguments] [options...]"
            + "\n\nFunctions:"
            + "\n\t" + CAESAR + "\t- Caesar Cipher"
            + "\n\t" + FREQ_ANALYSIS + "\t- Frequency Analysis"
            + "\n\nFrequency Analysis Arguments:"
            + "\n\t" + LETTER_FREQ_MATCH
            + " <text>\t\tStrict match the character frequencies in the provided text to the known character frequencies in English"
            + "\n\nCaesar Cipher Arguments:"
            + "\n\t[" + CAESAR_ENC + "|" + CAESAR_DEC + "]\t\t\tWhether to encipher or decipher"
            + "\n\t" + CAESAR_KEY + " <key>\t\tA number from 0 to 25 to act as the key"
            + "\n\t" + CAESAR_TXT + " <text>\t\tThe text to encipher or decipher"
            + "\n\tOR"
            + "\n\t" + CAESAR_AUTO + " <text>\t\tAttempt to automatically find the key"
            + "\n\nOptions:"
            + "\n\t" + HELP1 + "\t\t\tDisplays help"
            + "\n\nNOTE: TEXTINPUTMUSTNOTCONTAINSPACES";

    /**
     * Main method for running the programs
     * 
     * @param args Arguments to the program:
     *             [-e|-d] Whether to encrypt or decrypt
     *             -k <key> A number from 0 to 25
     *             -t <text> Text to be encrypted or decrypted
     */
    public static void main(String[] args) {
        if (!new ArgumentParser(args).parse()) {
            usage();
        }
        ;
    }

    /**
     * Print usage information and exit
     */
    private static void usage() {
        System.out.println(USAGE);
        System.exit(-1);
    }

    private static class ArgumentParser {
        private int i = 0;
        private String[] args;

        public ArgumentParser(String[] args) {
            this.args = args;
        }

        /**
         * Recursive descent parser for the arguments. Begins by parsing which function
         * the program has been called to perform
         * 
         * @param args Arguments
         */
        public boolean parse() {
            if (!parseToken(FUNC))
                return false;
            i++;
            if (parseToken(CAESAR)) {
                i++;
                return parseCaesarFunc();
            } else if (parseToken(FREQ_ANALYSIS)) {
                i++;
                return parseFreqFunc();
            } else {
                return false;
            }
        }

        private boolean parseFreqFunc() {
            if (parseToken(LETTER_FREQ_MATCH)) {
                System.out.println(FrequencyAnalyser.letterFreqDeviation(args[++i]));
                return true;
            } else {
                return false;
            }
        }

        private boolean parseCaesarFunc() {
            int encOrDec;
            if (parseToken(CAESAR_ENC)) {
                encOrDec = CaesarCipher.ENCIPHER;
            } else if (parseToken(CAESAR_DEC)) {
                encOrDec = CaesarCipher.DECIPHER;
            } else if (parseToken(CAESAR_AUTO)) {
                System.out.println(FrequencyAnalyser.pickLikelyPlaintext(CaesarCipher.autoDecipher(args[++i])));
                return true;
            } else {
                return false;
            }
            i++;
            if (!parseToken(CAESAR_KEY))
                return false;
            i++;
            int k = Integer.valueOf(args[i]);
            i++;
            if (!parseToken(CAESAR_TXT))
                return false;
            i++;
            String txt = args[i];
            System.out.println(CaesarCipher.rotate(txt, k, encOrDec));
            return true;
        }

        private boolean parseToken(String token) {
            return !(!(i < args.length) || args[i] == null || !args[i].equals(token));
        }
    }
}
