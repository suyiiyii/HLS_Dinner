package top.suyiiyii.Su.orm.struct;

import top.suyiiyii.Su.orm.core.Warpper;

import java.sql.SQLException;

public interface CallBack {
    Object call(Warpper query) throws SQLException;
}
