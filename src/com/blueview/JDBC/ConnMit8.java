package com.blueview.JDBC;
import util.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class ConnMit8 {

    /*
        事务
     */

    public static void main(String[] args) {
        Connection conn = null  ;
        PreparedStatement pstat1 = null ;
        PreparedStatement pstat2 = null ;

        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);  // false 开启事务
            String sql1 = "update Meney set sala = sala + 500 where name = ?";
            String sql2 = "update Meney set sala = sala - 500 where name = ?";

            pstat1 = conn.prepareStatement(sql1);
            pstat2 = conn.prepareStatement(sql2);

            pstat1.setString(1,"lucasie");
            pstat2.setString(1,"admin");

            int i1 = pstat1.executeUpdate();
            int i2 = pstat2.executeUpdate();
            conn.commit(); // 事务提交

        } catch (Exception e) { // 异常权限需要大于其他异常

            if (conn != null){
                try {
                    conn.rollback(); // 事务回滚
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();

        } finally {
          JDBCUtils.close(pstat1,conn);
          JDBCUtils.close(pstat2,null ); // 重复关闭相同资源,传null即可
        }
    }
}
