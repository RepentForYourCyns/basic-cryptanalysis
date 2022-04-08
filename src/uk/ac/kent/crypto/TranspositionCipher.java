package uk.ac.kent.crypto;

import java.util.ArrayList;

public class TranspositionCipher {
    public static String encipher(String in, String key) {
        key = key.toUpperCase();
        ArrayList<Character>[] columns = new ArrayList[key.length()];
        for(int i = 0; i < columns.length; i ++) {
            columns[i] = new ArrayList<>();
        }
        for(int i = 0; i < in.length(); i++) {
            columns[i % key.length()].add(Character.toUpperCase(in.charAt(i)));
        }
        Integer[] order = new Integer[key.length()];
        for(int i = 0; i < order.length; i++) {
            int minLetter = Integer.MAX_VALUE;
            int minIndex = 0;
            for(int j = 0; j < key.length(); j++) {
                if(key.charAt(j) < minLetter && !contains(order, j)) {
                    minLetter = key.charAt(j);
                    minIndex = j;
                }
            }
            order[i] = minIndex;
        }
        String out = "";
        for(int col : order) {
            for(Character ch : columns[col]) {
                out += ch;
            }
        }
        return out;
    }

    public static String decipher(String in, String key) {
        return null;
    }

    private static boolean contains(Integer[] arr, int num) {
        for(int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i] == num) return true;
        }
        return false;
    }
}
