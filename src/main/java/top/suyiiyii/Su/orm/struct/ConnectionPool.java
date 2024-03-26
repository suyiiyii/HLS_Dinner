package top.suyiiyii.Su.orm.struct;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();

    void returnConnection(Connection conn);

}
