package com.blueview.JDBC;
import util.JDBCUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/*
    1.获取用户名和手机号
 */
public class GetUserInfo {
    LinkedHashMap<String, String> map;
    private String adder;
    private String name;
    private String phone;



    public GetUserInfo(String adder) {
        this.adder = adder;
    }

    public ArrayList<String> getUser() throws SQLException {
//        map = new LinkedHashMap<String,String>();
        ArrayList<String> list = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        Statement stat = conn.createStatement();
        String sql = "SELECT distinct a.f_username,a.f_mobile FROM cms_user a,(SELECT distinct bv_tm_user_name FROM blueview_terminal WHERE bv_tm_ipv6 LIKE '"+adder+"') b where a.f_username=b.bv_tm_user_name";
        ResultSet rest = stat.executeQuery(sql);
        while (rest.next()){
            name = rest.getString(1);
            phone = rest.getString(2);

            list.add(name);
            list.add(phone);

        }
        return list;
    }


}
