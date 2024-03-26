package top.suyiiyii.Su.orm.utils;

import top.suyiiyii.Su.orm.core.ConnectionManger;
import top.suyiiyii.Su.orm.struct.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * sql执行器
 * 对session负责（session的工具类）
 */
public class SqlExecutor {
    /**
     * 线程安全：对象仅作为session的工具类，线程安全同session
     */
    private final Connection conn;
    // 由于只有connection，无法直接归还连接，所以需要connectionManger
    private final ConnectionManger connectionManger;

    public SqlExecutor(Connection conn, ConnectionManger connectionManger) {
        this.conn = conn;
        this.connectionManger = connectionManger;
    }

    public Connection getConn() {
        return conn;
    }

    /**
     * 获取一个预编译的sql语句
     *
     * @param sql sql语句
     * @return 预编译的sql语句
     * @throws SQLException sql语句错误
     */
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    /**
     * 创建不存在的表
     *
     * @param table 表对象
     * @return 是否创建成功
     */
    public boolean createTable(Table table) {
        // 判断表是否存在
        String sql = TableSqlGenerater.getIsTableExistSql(table);
        ResultSet resultSet = query(sql);
        try {
            if (resultSet.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 不存在则创建
        sql = TableSqlGenerater.getCreateTableSql(table);
        return execute(sql);
    }

    /**
     * 执行语句 sql版
     *
     * @param sql sql语句
     * @return 是否执行成功
     */
    public boolean execute(String sql) {
        try {
            conn.createStatement().execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 执行语句 预编译版
     *
     * @param preparedStatement 预编译的sql语句
     * @return 是否执行成功
     */
    public boolean execute(PreparedStatement preparedStatement, boolean isBatch) {
        try {
            if (isBatch) {
                preparedStatement.executeBatch();
            } else {
                preparedStatement.execute();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionManger.returnConnection(conn);
        }
    }

    public boolean execute(PreparedStatement preparedStatement) {
        return execute(preparedStatement, false);
    }

    /**
     * 查询 sql版
     *
     * @param sql sql语句
     * @return 查询结果
     */
    public ResultSet query(String sql) {
        ResultSet resultSet = null;
        try {
            resultSet = conn.createStatement().executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * 查询 预编译版
     *
     * @param preparedStatement 预编译的sql语句
     * @return 查询结果
     */
    public ResultSet query(PreparedStatement preparedStatement) {
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void close() {
        connectionManger.returnConnection(conn);
    }
}