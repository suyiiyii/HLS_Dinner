package top.suyiiyii.Su.orm.utils;

import java.sql.Connection;

/**
 * 连接构建器
 * 调用getConnection方法将会返回一个连接
 */
public class ConnectionBuilder {
    /**
     * 线程安全：只读，不涉及线程安全问题
     */
    private final String url;
    private final String username;
    private final String password;

    public ConnectionBuilder(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        // 加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return java.sql.DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
