package com.example.arraystring;

import java.util.ArrayList;
import java.util.List;

public class MergeTwoArray {

    public static void main(String[] args) {
        System.out.println("xxx");
        int[] nums1 = {-1,0,0,3,3,3,0,0,0};
        int m = 6;
        int[] nums2 = {1,2,2};
        int n = 3;
        merge(nums1,m,nums2,n);
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {

        List<Integer> list = new ArrayList<>();
        int j = 0;
        int i = 0;
        for (; i < m; ){
            if(i == m || j == n){
                break;
            }

            if(nums1[i] <= nums2[j]){
                list.add(nums1[i]);
                i++;
            }else {
                list.add(nums2[j]);
                j++;
            }
        }

        if(j<n){
            for (; j < n; j++) {
                list.add(nums2[j]);
            }
        }else {
            for (; i < m; i++) {
                list.add(nums1[i]);
            }
        }

        for (int r = 0; r < list.size(); r++) {
            nums1[r] = list.get(r);
        }

        System.out.println(list);


    }
}
