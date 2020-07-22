package com.blueview.JDBC;

import util.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
        获取用户名和手机号
 */
public class BlueViewUser {

    public static void main(String[] args) throws SQLException {

        ArrayList<String> list = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        Statement stat = conn.createStatement();
        String adder = "8643880457797032b88";
        String sql = "SELECT distinct a.f_username,a.f_mobile,b.bv_tm_install_addr  FROM cms_user a,(SELECT bv_tm_user_name,bv_tm_install_addr FROM blueview_terminal WHERE bv_tm_ipv6 = '"+adder+"') b where a.f_username=b.bv_tm_user_name";
        ResultSet res = stat.executeQuery(sql);
        while (res.next()){
            String name = res.getString(1);
            String phone = res.getString(2);
            String adders = res.getString(3);
            list.add(name);
            list.add(phone);
            list.add(adders);
            System.out.println(list);
        }

        JDBCUtils.close(res,stat,conn);
    }
}
