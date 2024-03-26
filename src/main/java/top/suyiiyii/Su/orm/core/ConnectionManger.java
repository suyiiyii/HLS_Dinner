package top.suyiiyii.Su.orm.core;

import top.suyiiyii.Su.orm.struct.ConnectionPool;
import top.suyiiyii.Su.orm.utils.SqlExecutor;
import top.suyiiyii.Su.orm.utils.SuConnectionPool;

import java.sql.Connection;
import java.util.concurrent.Callable;


/**
 * 与数据库直接相关的操作
 * 保存了连接池
 */
public class ConnectionManger {
    /**
     * 线程安全：对象在构造函数中初始化，次后只读，不涉及线程安全问题
     */
    private final ConnectionPool connectionPool;

    public ConnectionManger(Callable<Connection> connectionBuilder) {
        connectionPool = new SuConnectionPool(20, 2, connectionBuilder);
    }

    public SqlExecutor getSqlExecutor() {
        return new SqlExecutor(connectionPool.getConnection(), this);
    }

    public void returnConnection(Connection conn) {
        connectionPool.returnConnection(conn);
    }


}
