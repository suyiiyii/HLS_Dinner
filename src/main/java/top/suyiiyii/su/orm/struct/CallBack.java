package top.suyiiyii.su.orm.struct;

import top.suyiiyii.su.orm.core.Warpper;

import java.sql.SQLException;

/**
 * 回调接口
 * 用于执行sql语句
 *
 * @author suyiiyii
 */
public interface CallBack {
    Object call(Warpper query) throws SQLException;
}
