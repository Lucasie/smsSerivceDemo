package com.blueview.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
    增加数据
 */
public class jdbc2 {

    public static void main(String[] args) {
        // 释放资源需要把释放的资源定义成成员变量
        Statement stat = null;
        Connection conn = null;

        try {
            // 1. 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2.创建comm
            conn = DriverManager.getConnection("jdbc:mysql://lucasie.info:3306/JavaTest", "Lucasie", "Love1314");

            // 3.定义mysql语句
            String sql = "insert into User values(null,'小张',2000)";    // 新增数据
//            String sql = "update User set 金额 = 500 where 金额 = 1000";      // 更新数据

            // 4 获取执行sql Statement对象
            stat = conn.createStatement();

            // 5.查询sql
            int count = stat.executeUpdate(sql);

            // 6.处理结果
            System.out.println(count);
            if (count > 0){
                System.out.println("添加成功");
            }else {
                System.out.println("添加失败");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            //7.释放资源
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
