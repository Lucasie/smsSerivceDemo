package util;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;


public class JDBCUtils {
    // 定义变量
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    // 随着类加载,且只加载一次
    static {

        try {
            // 创建Properties对象
            Properties pro = new Properties();
            // 动态获取配置文件,classLoad类加载器
            ClassLoader load = JDBCUtils.class.getClassLoader(); // src路径下
            // 获取资源
            URL res = load.getResource("jdbc.properties");
            String path = res.getPath();
            // 加载文件,静态方法里智能抓不能抛异常
//            pro.load(new FileReader("E:\\JAVA\\smsSerivce\\src\\jdbc.properties"));
            pro.load(new FileReader(path));
            // 获取配置文件属性
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            password = pro.getProperty("password");
            driver = pro.getProperty("driver");
            // 注册驱动
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // 获取连接方法
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    // 关闭资源
    public  static void close(Statement stat, Connection conn){
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

    // 重载
    public  static void close(ResultSet rst, Statement stat, Connection conn){
        if (rst != null){
            try {
                rst.close();
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
