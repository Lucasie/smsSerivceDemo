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
    private String installAdder;


    public GetUserInfo(String adder) {
        this.adder = adder;
    }

    public ArrayList<String> getUser() throws SQLException {
//        map = new LinkedHashMap<String,String>();
        ArrayList<String> list = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        Statement stat = conn.createStatement();
        String sql = "SELECT distinct a.f_username,a.f_mobile,b.bv_tm_install_addr  FROM cms_user a,(SELECT bv_tm_user_name,bv_tm_install_addr FROM blueview_terminal WHERE bv_tm_ipv6 = '"+adder+"') b where a.f_username=b.bv_tm_user_name";
        ResultSet rest = stat.executeQuery(sql);
        while (rest.next()){
            name = rest.getString(1);
            phone = rest.getString(2);
            installAdder = rest.getString(3);
            list.add(name);
            list.add(phone);
            list.add(installAdder);
        }
        return list;
    }


}
