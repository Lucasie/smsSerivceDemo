package com.blueview.JDBC;

/*
    查询
 */

import util.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class jdbc6 {


    public static void main(String[] args) throws SQLException {

        System.out.println("Name:");
        String sc = new Scanner(System.in).next();
        System.out.println("Password:");
        String pd = new Scanner(System.in).next();

        boolean t = findUSer(sc, pd);
        if (t){
            System.out.println("成功");
        }else {
            System.out.println("失败");
        }
    }


    public static boolean findUSer(String name,String password) throws SQLException {

        if (name != null && password != null){
            Connection conn = JDBCUtils.getConnection();
            String sql = "select * from User where name = '"+name+"' and Password = '"+password+"'";
            Statement stat = conn.createStatement();
            System.out.println(sql);
            ResultSet resultSet = stat.executeQuery(sql);
            return resultSet.next();
        }else{
            return false;
        }

    }
}


