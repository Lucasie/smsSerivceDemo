package com.blueview.MQTT;


import com.blueview.JDBC.GetUserInfo;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class test {


    public static void main(String[] args) throws SQLException {
        ArrayList<String> user = new GetUserInfo("864388045779703%").getUser();
        for (String item : user) {
            System.out.println(item);
        }
    }
}