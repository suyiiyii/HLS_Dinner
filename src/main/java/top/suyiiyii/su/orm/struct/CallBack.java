package top.suyiiyii.su.orm.struct;

import top.suyiiyii.su.orm.core.Warpper;

import java.sql.SQLException;

public interface CallBack {
    Object call(Warpper query) throws SQLException;
}
