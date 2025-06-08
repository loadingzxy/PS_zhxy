package com.example.arraystring;

public class canCompleteCircuit {
    public int canCompleteCircuit(int[] gas, int[] cost) {

//        int result = -1;
//
//        for (int i = 0; i < gas.length; i++) {
//            int gasSum = 0;
//            if(gas[i] == 0){
//                continue;
//            }
//            if(gas[i] >= cost[i]){
//                gasSum = gas[i] - cost[i];
//                for (int j = i+1; j <= gas.length; j++){
//                    if(j == gas.length){
//                        j = 0;
//                    }
//                    if(j == i){
//                        return i;
//                    }
//
//                    gasSum = gasSum + gas[j] - cost[j];
//
//                    if(gasSum < 0){
//                        break;
//                    }
//                }
//            }
//
//
//        }
//
//
//        return result;


        int n = gas.length;
        int i = 0;
        while (i < n) {
            int sumOfGas = 0, sumOfCost = 0;
            int cnt = 0;
            while (cnt < n) {
                int j = (i + cnt) % n;
                sumOfGas += gas[j];
                sumOfCost += cost[j];
                if (sumOfCost > sumOfGas) {
                    break;
                }
                cnt++;
            }
            if (cnt == n) {
                return i;
            } else {
                i = i + cnt + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] gas = {1,2,3,4,5};
        int[] cost = {3,4,5,1,2};
        System.out.println(new canCompleteCircuit().canCompleteCircuit(gas, cost));
    }
}
