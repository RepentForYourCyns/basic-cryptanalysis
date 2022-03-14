package uk.ac.kent.crypto;

public class FrequencyAnalyser {
    /**
     * Represent letter of alphabet along with its frequency in American English
     */
    private enum Letter {
        A(1.0f), B(1.0f), C(0), D(0), E(0), F(0), G(0), H(0), I(0), J(0), K(0), L(0), M(0), N(0), O(0), P(0), Q(0),
        R(0), S(0), T(0), U(0), V(0), W(0), X(0), Y(0), Z(0);

        float freq;

        /**
         * Constructor for Letter enums
         * 
         * @param freq Frequency of this letter in the American English corpuss
         */
        Letter(float freq) {
            this.freq = freq;
        }
    }

    // TODO Method(s) to determine consistency of input with letter frequency
    // Methodology: Stallings et al have work on the frequency of particular letters
    // in the english corpus. Use the ORDERING of letter frequencies, starting with
    // the one that Stallings et al come up with and then swapping around the 10 or
    // so most commong letters to see if the input matches this ordering instead.
}
