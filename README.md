# README

A project in basic cryptanalysis. Contains some basic cryptanalytic algorithms that can be imported into other programs and a command line program for running them standalone.

## Frequency Analysis

Functionality for analysing letter, bigram and trigram frequency in supplied text. Can be used to help break ciphers or to determine if output of an attempted decipher is likely to be plaintext.

## Caesar Cipher

Functionality for enciphering or deciphering text using a Caesar cipher algorithm. Also capable of attempting an automatic search across possible keys for the correct key, using frequency analysis:
1. Decipher the text using each of 25 possible keys
2. Analyse the letter frequency distributions in each of these outputs, assigning a value that represents how different the distribution is
3. Choose the key whose deciphered text has the lowest associated value

## Vigenere Cipher

Functionality for enciphering or deciphering text using a Vigenere cipher algorithm. Also capable of attempting an automatic search for the key, using frequency analysis.

Search knowing key length:
1. Alternate placing letters in slots of an array that correspond to the letters in the key. All of the letters in a slot therefore have the same key value
2. Use the algorithm for the Caesar cipher above to crack the key for each of these slots
3. The key for each of the slots in order forms the key for the cipher

Search not knowing key length:
1. Look for repititions in the ciphertext that indicate the same part of the key has matched up with the same plaintext letters
2. Any common multiples between the sizes of the gaps between repitions found are possible key lengths
3. Use the algorithm above with each of the found key lengths