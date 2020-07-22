package com.blueview.JDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    将数据封装成List集合
 */

public class UserInfoFindAll {

    public List<UserInfo> findAll(){

        Connection conn = null;
        Statement stat = null;
        ResultSet result = null;
        List<UserInfo> userList = null;

        // ODBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://lucasie.info:3306/JavaTest", "Lucasie", "Love1314");
            String sql = "select * from User";
            stat = conn.createStatement();
            result = stat.executeQuery(sql);
            // 创建对象的引用,避免过多堆内存
            UserInfo userInfo = null;
            userList  = new ArrayList<UserInfo>();
            while (result.next()){
                int id = result.getInt(1);
                String name = result.getString(2);
                double salary = result.getDouble(3);

                // 引用复用
                userInfo = new UserInfo();
                userInfo.setId(id);
                userInfo.setName(name);
                userInfo.setSalary(salary);
                // 装载集合
                userList.add(userInfo);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (result != null){
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stat != null) {
                try {
                    result.close();
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

        return userList;
    }
}
