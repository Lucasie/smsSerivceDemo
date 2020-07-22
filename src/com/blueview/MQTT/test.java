package com.blueview.MQTT;


import java.sql.SQLOutput;
import java.util.Scanner;

public class test {




    public static void main(String[] args) {
//        int num=10;
//        StringBuilder sb = new StringBuilder();
//        if (num<=0xf&&num>=0){
//            sb.append(00);
//        }
//        sb.append(Integer.toHexString(0xff&num));
//        System.out.println(sb);
        String[] s = new String[] {"0","0","0","0"};
        String str = "1000";
        String s1 = Integer.toHexString(Integer.valueOf(str));
        System.out.println(s1);
        int len = s1.length();

        System.out.println(len);
        for (int length = s1.length(); length > 0; length--) {
            System.out.println("位置：" + (length-1));

//            s[len+length-1] = s1.substring(length);
//            System.out.println(len);
            if (len > 2){
                s[length]= s1.substring(length-1);
//                System.out.println(length-1);
            }
//            s[len+length-1] = s1.substring(length);
        }
        for (String item : s) {
            System.out.print(item);
        }
    }

}