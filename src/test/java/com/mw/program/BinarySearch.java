package com.mw.program;

/**
 * @author WangCH
 * @create 2018-03-12 20:34
 */
public class BinarySearch {


    /**
     * 二分查找
     * @param arr 有序
     * @param k
     * @return
     */
    public int binarySearch(int[] arr,int k){

        int a = 0;
        int b = arr.length;
        //[a,b)
        while(a < b){
            //int m = (a + b) / 2;
            int m = a + (b - a) / 2;
            if(k < arr[m]){//a...(m-1)
                b = m;
            }else if(k > arr[m]){//(m+1)...b
                a = m + 1;
            }else{
                return m;
            }
        }
        return -1;
    }

    public static void main(String[] args) {

        BinarySearch bs = new BinarySearch();
        System.out.println(
                bs.binarySearch(new int[]{1,2,10,15,100},15));
        System.out.println(
                bs.binarySearch(new int[]{1,2,10,15,100},-2));
        System.out.println(
                bs.binarySearch(new int[]{1,2,10,15,100},101));
        System.out.println(
                bs.binarySearch(new int[]{1,2,10,15,100},13));
        System.out.println("=========================");
        System.out.println(
                bs.binarySearch(new int[]{},13));
        System.out.println(
                bs.binarySearch(new int[]{1},13));
        System.out.println(
                bs.binarySearch(new int[]{13},13));
        System.out.println("=========================");
        System.out.println(
                bs.binarySearch(new int[]{12,13},12));
        System.out.println(
                bs.binarySearch(new int[]{12,13},13));
        //java.nio.Buffer
    }
}
