package com.example.arraystring;

public class rotate {
    public void rotate(int[] nums, int k) {

        if(k > nums.length){
            k = k % nums.length;
        }

        int[] temp = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            if(i+k < nums.length){
                temp[i+k] = nums[i];
            }
            else{
                temp[i+k-nums.length] = nums[i];
            }
        }

        for (int i = 0; i < nums.length; i++) {
            nums[i] = temp[i];
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2};
        new rotate().rotate(nums, 3);
    }
}
