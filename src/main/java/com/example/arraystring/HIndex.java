package com.example.arraystring;

import java.util.Arrays;

public class HIndex {

    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int h = 0, i = citations.length - 1;
        while (i >= 0 && citations[i] > h) {
            h++;
            i--;
        }
        return h;
    }

    public static void main(String[] args) {
        int[] citations = {3,0,6,1,5};
        System.out.println(new HIndex().hIndex(citations));
    }
}
