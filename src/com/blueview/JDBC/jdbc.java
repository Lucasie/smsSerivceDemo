package com.blueview.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class jdbc {


    public static void main(String[] args) throws Exception {

        /*
            1.导入包
                1.将jar包导入到项目
                2.右键-右键-->Add As Library
         */


        // 2注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 3.获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://blueview.hk:18306/blueviewbv", "root", "blueview2018");

        // 4.定义sql语句
        String str = "SELECT f_user_id FROM cms_user ";

        // 5.获取执行sql语句的对象 Statement
        Statement stat = conn.createStatement();

        // 6.执行SQL语句
        ResultSet execute = stat.executeQuery(str);

        // 7.处理结果
        System.out.println(execute);

        // 8.关闭资源
        stat.close();
        conn.close();

    }

}
