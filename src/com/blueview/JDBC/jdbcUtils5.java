package com.blueview.JDBC;

import util.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcUtils5 {

    public static void main(String[] args) throws SQLException {

        // 查询JavaTest数据所有信息
        //1 .获取comm连接对象
        Connection conn = JDBCUtils.getConnection();
        // 2.编写sql语句
        String sql = "select * from User";
        // 3.获取statement
        Statement stat = conn.createStatement();
        ResultSet res = stat.executeQuery(sql);
        while (res.next()){
            int id = res.getInt(1);
            String name = res.getString(2);
            double salaly = res.getDouble(3);
            System.out.println(id +":"+ name+":"+salaly);
        }
        JDBCUtils.close(res,stat,conn);
    }
}
