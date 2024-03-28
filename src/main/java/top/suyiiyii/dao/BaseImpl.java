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
        try {
            db.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commitTransaction() {
        try {
            db.commit();
            db.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollbackTransaction() {
        try {
            db.rollback();
            db.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
