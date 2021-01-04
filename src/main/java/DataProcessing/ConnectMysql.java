package DataProcessing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class ConnectMysql {
    // 连接数据库
    public static Connection getConnection() {
        Connection conn = null;
        String JDBC_URL = "jdbc:mysql://localhost:3306/daily?characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "l.1322630122";
        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
