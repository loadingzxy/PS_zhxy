package com.example.arraystring;

public class CanJump {
    public boolean canJump(int[] nums) {

        for (int i = 0; i < nums.length; i++){
            if(nums[i] == 0){
                if(i == nums.length - 1 ){
                    return true;
                }
                if (i == 0){
                    return false;
                }

                for(int j = i-1; j >= 0; j--){
                    if(nums[j] > i - j){
                        break;
                    }
                    if(j == 0){
                        return false;
                    }
                }
            }

        }
        return true;
    }

    public int jump(int[] nums) {

        int count = 0;
        int max = 0;
        int cur = 0;
        for (int i = 0; i < nums.length-1; i++){
            max = Math.max(max, i + nums[i]);
            if(i == cur){
                cur = max;
                count++;
            }
        }
        return count;


    }





    public static void main(String[] args) {
        System.out.println(new CanJump().canJump(new int[]{0,1}));
    }
}
