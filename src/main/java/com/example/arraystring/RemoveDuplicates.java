package com.example.arraystring;

public class RemoveDuplicates {

    public int removeDuplicates(int[] nums) {

        // 同向双指针
        int n = nums.length;
        int l = 2;
        for(int r = l; r < n; ++r){
            if(nums[r] != nums[l - 2]){
                nums[l] = nums[r];
                l++;
            }
        }
        return l;



    }

    public static void main(String[] args) {
       int[] nums = {0,0,1,1,1,1,2,3,3};
       System.out.println(new RemoveDuplicates().removeDuplicates(nums));
    }
}
