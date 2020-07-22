package com.blueview.JDBC;

/*
    查询
    PreparedStatement() 预封装sql语句
 */

import util.JDBCUtils;

import java.sql.*;
import java.util.Scanner;

public class jdbc7 {


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
            String sql = "select * from User where name = ? and Password = ?";
//            Statement stat = conn.createStatement();
            PreparedStatement pstat = conn.prepareStatement(sql); // 创建PreparedStatement对象
            pstat.setString(1,name);    // 设置?的位置然后是参数
            pstat.setString(2,password);
            ResultSet rest = pstat.executeQuery(); // 执行sql 不需要传递参数
//            System.out.println(sql);
//            ResultSet resultSet = stat.executeQuery(sql);
            return rest.next();
        }else{
            return false;
        }

    }
}


