package top.suyiiyii.su.orm.struct;

import java.sql.Connection;

/**
 * 连接池接口
 *
 * @author suyiiyii
 */
public interface ConnectionPool {
    Connection getConnection();

    void returnConnection(Connection conn);

}
