package top.suyiiyii.dao;

import top.suyiiyii.su.orm.core.Session;

import java.sql.SQLException;

public class BaseImpl implements BaseDAO {
    Session db;

    public BaseImpl(Session db) {
        this.db = db;
    }

    @Override
    public void beginTransaction() {
        db.beginTransaction();
    }

    @Override
    public void commitTransaction() {
        db.commitTransaction();
    }

    @Override
    public void rollbackTransaction() {
        db.rollbackTransaction();
    }
}
