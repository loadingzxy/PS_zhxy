package com.example.arraystring;

public class MaxProfit {

    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++){
            for (int j = i+1; j < prices.length; j++){
                if (prices[j] > prices[i]){
                    maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
                }
            }
        }
        return maxProfit;

    }

    public int maxProfi2(int[] prices) {
        int maxProfit = 0;
        for (int i = 0; i < prices.length-1; i++) {
            if(prices[i+1]>prices[i]){
                maxProfit += prices[i+1] - prices[i];
            }
        }

        return maxProfit;

    }

    public static void main(String[] args) {
        System.out.println(new MaxProfit().maxProfi2(new int[]{7,1,5,3,6,4}));
    }
}
