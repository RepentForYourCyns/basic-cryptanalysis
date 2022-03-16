package uk.ac.kent.crypto;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class for performing frequency analysis on either ciphertext or as a
 * heuristic to determine whether output text is likely correct.
 * 
 * Frequencies taken from:
 * http://mathcenter.oxford.emory.edu/site/math125/englishLetterFreqs/
 */
public class FrequencyAnalyser {
    // 0.0000100000000002 Absolute freq error
    private static final Letter[] ALPHABET = { Letter.A, Letter.B, Letter.C, Letter.D, Letter.E, Letter.F, Letter.G,
            Letter.H, Letter.I, Letter.J, Letter.K, Letter.L, Letter.M, Letter.N, Letter.O, Letter.P, Letter.Q,
            Letter.R, Letter.S, Letter.T, Letter.U, Letter.V, Letter.W, Letter.X, Letter.Y, Letter.Z };
    private static final Letter[] ALPHABET_BY_FREQ = { Letter.E, Letter.T, Letter.A, Letter.O, Letter.I, Letter.N,
            Letter.S, Letter.H, Letter.R, Letter.D, Letter.L, Letter.C, Letter.U, Letter.M, Letter.W, Letter.F,
            Letter.G, Letter.Y, Letter.P, Letter.B, Letter.V, Letter.K, Letter.J, Letter.X, Letter.Q, Letter.Z };

    // Relative frequencies of bigrams and trigrams, absolute not stored
    private static final Bigram[] BIGRAMS_BY_FREQ = { new Bigram(Letter.T, Letter.H), new Bigram(Letter.H, Letter.E),
            new Bigram(Letter.I, Letter.N), new Bigram(Letter.E, Letter.N), new Bigram(Letter.N, Letter.T),
            new Bigram(Letter.R, Letter.E), new Bigram(Letter.E, Letter.R), new Bigram(Letter.A, Letter.N),
            new Bigram(Letter.T, Letter.I), new Bigram(Letter.E, Letter.S), new Bigram(Letter.O, Letter.N),
            new Bigram(Letter.A, Letter.T), new Bigram(Letter.S, Letter.E), new Bigram(Letter.N, Letter.D),
            new Bigram(Letter.O, Letter.R), new Bigram(Letter.A, Letter.R), new Bigram(Letter.A, Letter.L),
            new Bigram(Letter.T, Letter.E), new Bigram(Letter.C, Letter.O), new Bigram(Letter.D, Letter.E),
            new Bigram(Letter.T, Letter.O), new Bigram(Letter.R, Letter.A), new Bigram(Letter.E, Letter.T),
            new Bigram(Letter.E, Letter.D), new Bigram(Letter.I, Letter.T), new Bigram(Letter.S, Letter.A),
            new Bigram(Letter.E, Letter.M), new Bigram(Letter.R, Letter.O) };
    private static final Trigram[] TRIGRAMS_BY_FREQ = { new Trigram(Letter.T, Letter.H, Letter.E),
            new Trigram(Letter.A, Letter.N, Letter.D), new Trigram(Letter.T, Letter.H, Letter.A),
            new Trigram(Letter.E, Letter.N, Letter.T), new Trigram(Letter.I, Letter.N, Letter.G),
            new Trigram(Letter.I, Letter.O, Letter.N), new Trigram(Letter.T, Letter.I, Letter.O),
            new Trigram(Letter.F, Letter.O, Letter.R), new Trigram(Letter.N, Letter.D, Letter.E),
            new Trigram(Letter.H, Letter.A, Letter.S), new Trigram(Letter.N, Letter.C, Letter.E),
            new Trigram(Letter.E, Letter.D, Letter.T), new Trigram(Letter.T, Letter.I, Letter.S),
            new Trigram(Letter.O, Letter.F, Letter.T), new Trigram(Letter.S, Letter.T, Letter.H),
            new Trigram(Letter.M, Letter.E, Letter.N) };

    /**
     * Represent letter of alphabet along with its frequency in British English. Not
     * case sensitive
     */
    private enum Letter {
        A(0.08167d, 'A'), B(0.01492d, 'B'), C(0.02782d, 'C'), D(0.04253d, 'D'), E(0.12702d, 'E'), F(0.02228d, 'F'),
        G(0.02015d, 'G'), H(0.06094d, 'H'), I(0.06966d, 'I'), J(0.00153d, 'J'), K(0.00772d, 'K'), L(0.04025d, 'L'),
        M(0.02406d, 'M'), N(0.06749d, 'N'), O(0.07507d, 'O'), P(0.01929d, 'P'), Q(0.00095d, 'Q'), R(0.05987d, 'R'),
        S(0.06327d, 'S'), T(0.09056d, 'T'), U(0.02758d, 'U'), V(0.00978d, 'V'), W(0.02360d, 'W'), X(0.00150d, 'X'),
        Y(0.01974d, 'Y'), Z(0.00074d, 'Z');

        double freq;
        char chUpper;
        char chLower;

        /**
         * Constructor for Letter enums
         * 
         * @param freq Frequency of this letter in the British English corpus
         */
        Letter(double freq, char chUpper) {
            this.freq = freq;
            this.chUpper = chUpper;
            this.chLower = Character.toLowerCase(chUpper);
        }
    }

    /**
     * Represent a bigram (pair of letters)
     */
    private static class Bigram {
        private Letter l1;
        private Letter l2;

        /**
         * Construct a bigram
         * 
         * @param l1 First letter
         * @param l2 Second letter
         */
        public Bigram(Letter l1, Letter l2) {
            this.l1 = l1;
            this.l2 = l2;
        }

        /**
         * Get this bigram as a string
         * 
         * @return two-letter string
         */
        public String getString() {
            return "" + l1.chUpper + l2.chUpper;
        }
    }

    /**
     * Represents a trigram (triplet of letters)
     */
    private static class Trigram {
        private Letter l1;
        private Letter l2;
        private Letter l3;

        /**
         * Construct a trigram
         * 
         * @param l1 First letter
         * @param l2 Second letter
         * @param l3 Third letter
         */
        public Trigram(Letter l1, Letter l2, Letter l3) {
            this.l1 = l1;
            this.l2 = l2;
            this.l3 = l3;
        }

        /**
         * Get this trigram as a string
         * 
         * @return three-letter string
         */
        public String getString() {
            return "" + l1.chUpper + l2.chUpper + l3.chUpper;
        }
    }

    /**
     * Compute how different the letter frequency profile in the given text is to the
     * English corpus
     * 
     * @param text Text to test letter frequency of
     * @return A number representing how different the letter frequency in the text is from the English corpus
     */
    public static int letterFreqDeviation(String text) {
        ArrayList<Letter> letters = new ArrayList<>();
        int letterPointer = 0;
        Letter letter;
        do {
            letter = charToLetter(text.charAt(letterPointer));
            if (letter != null) {
                letters.add(letter);
            }
            letterPointer++;
        } while (letterPointer < text.length());
        class LetterFreq {
            private Letter letter;
            private int count;

            public LetterFreq(Letter letter) {
                this.letter = letter;
                this.count = 0;
            }
        }
        ArrayList<LetterFreq> letterFreqs = new ArrayList<>();
        for (Letter l : ALPHABET) {
            letterFreqs.add(new LetterFreq(l));
        }
        for (Letter l : letters) {
            // TODO Could use better search algorithm
            for (LetterFreq lf : letterFreqs) {
                if (lf.letter.equals(l)) {
                    lf.count++;
                }
            }
        }
        letterFreqs.sort(new Comparator<LetterFreq>() {
            @Override
            public int compare(LetterFreq o1, LetterFreq o2) {
                return o2.count - o1.count;
            }
        });
        Letter[] lettersByFreq = new Letter[letterFreqs.size()];
        for (int i = 0; i < lettersByFreq.length; i++) {
            lettersByFreq[i] = letterFreqs.get(i).letter;
        }
        return compareRelativeFrequencies(lettersByFreq, ALPHABET_BY_FREQ);
    }

    /**
     * Turn a character into its letter enumeration
     * 
     * @param ch An alphabetic character [a-zA-Z]
     * @return The equivalent letter character enumeration, or null if the input
     *         character is not alphabetic
     */
    private static Letter charToLetter(char ch) {
        switch (ch) {
            case 'A':
                return Letter.A;
            case 'a':
                return Letter.A;
            case 'B':
                return Letter.B;
            case 'b':
                return Letter.B;
            case 'C':
                return Letter.C;
            case 'c':
                return Letter.C;
            case 'D':
                return Letter.D;
            case 'd':
                return Letter.D;
            case 'E':
                return Letter.E;
            case 'e':
                return Letter.E;
            case 'F':
                return Letter.F;
            case 'f':
                return Letter.F;
            case 'G':
                return Letter.G;
            case 'g':
                return Letter.G;
            case 'H':
                return Letter.H;
            case 'h':
                return Letter.H;
            case 'I':
                return Letter.I;
            case 'i':
                return Letter.I;
            case 'J':
                return Letter.J;
            case 'j':
                return Letter.J;
            case 'K':
                return Letter.K;
            case 'k':
                return Letter.K;
            case 'L':
                return Letter.L;
            case 'l':
                return Letter.L;
            case 'M':
                return Letter.M;
            case 'm':
                return Letter.M;
            case 'N':
                return Letter.N;
            case 'n':
                return Letter.N;
            case 'O':
                return Letter.O;
            case 'o':
                return Letter.O;
            case 'P':
                return Letter.P;
            case 'p':
                return Letter.P;
            case 'Q':
                return Letter.Q;
            case 'q':
                return Letter.Q;
            case 'R':
                return Letter.R;
            case 'r':
                return Letter.R;
            case 'S':
                return Letter.S;
            case 's':
                return Letter.S;
            case 'T':
                return Letter.T;
            case 't':
                return Letter.T;
            case 'U':
                return Letter.U;
            case 'u':
                return Letter.U;
            case 'V':
                return Letter.V;
            case 'v':
                return Letter.V;
            case 'W':
                return Letter.W;
            case 'w':
                return Letter.W;
            case 'X':
                return Letter.X;
            case 'x':
                return Letter.X;
            case 'Y':
                return Letter.Y;
            case 'y':
                return Letter.Y;
            case 'Z':
                return Letter.Z;
            case 'z':
                return Letter.Z;
            default:
                return null;
        }
    }

    /**
     * Compare two sets of letter frequency results
     * 
     * @param freq1 An array containing every letter of the alphabet in order of
     *              their frequency
     * @param freq2 An array containing every letter of the alphabet in order of
     *              their frequency
     * @return A number representing how different they are
     */
    public static int compareRelativeFrequencies(Letter[] freq1, Letter[] freq2) {
        int diff = 0;
        for (int i = 0; i < freq1.length; i++) {
            Letter li = freq1[i];
            for (int j = 0; j < freq2.length; j++) {
                Letter lj = freq2[j];
                if (li.equals(lj)) {
                    diff += Math.abs(i - j);
                }
            }
        }
        return diff;
    }

    public static String pickLikelyPlaintext(ArrayList<String> in) {
        int[] diffs = new int[in.size()];
        for(int i = 0; i < diffs.length; i++) {
            diffs[i] = FrequencyAnalyser.letterFreqDeviation(in.get(i));
        }
        int smallestDiff = Integer.MAX_VALUE;
        int smallestDiffIndex = 0;
        for(int i = 0; i < diffs.length; i++) {
            if(diffs[i] < smallestDiff) {
                smallestDiff = diffs[i];
                smallestDiffIndex = i;
            }
        }
        return in.get(smallestDiffIndex);
    }

    // Methodology: Stallings et al have work on the frequency of particular letters
    // in the english corpus. Use the ORDERING of letter frequencies, starting with
    // the one that Stallings et al come up with and then swapping around the 10 or
    // so most commong letters to see if the input matches this ordering instead?
}
