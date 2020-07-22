package com.blueview.JDBC;

import java.sql.*;

public class jdbc3 {

    public static void main(String[] args) {
        // 释放资源需要把释放的资源定义成成员变量
        Statement stat = null;
        Connection conn = null;
        ResultSet resultset = null;


        try {
            // 1. 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2.创建comm
            conn = DriverManager.getConnection("jdbc:mysql://lucasie.info:3306/JavaTest", "Lucasie", "Love1314");

            // 3.定义mysql语句
            String sql = "select * FROM USER ";      // 更新数据

            // 4 获取执行sql Statement对象
            stat = conn.createStatement();

            // 5.查询sql
            resultset = stat.executeQuery(sql);

            // 6.处理结果
            /*
                .next()方法返回的是布尔值,直到为false就停止后移
                .getString()有很多重载方法,可以写数字列的位置:1,或者写列名称
             */
            while (resultset.next()){
                int id = resultset.getInt(1);
                String name = resultset.getString("用户名");
                double aDouble = resultset.getDouble(3);
                System.out.println(id +"--" + name + "--" + aDouble);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //7.释放资源
            if (resultset != null){
                try {
                    resultset.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (stat != null){
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
