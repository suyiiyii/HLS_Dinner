package top.suyiiyii.dao;

import top.suyiiyii.models.Table;
import top.suyiiyii.su.orm.core.Session;

import java.sql.SQLException;
import java.util.List;

/**
 * TablesImpl TableDAO的实现
 * 处理与数据库有关的操作
 * 仅处理和SQL有关的异常
 *
 * @author suyiiyii
 */
public class TablesImpl extends BaseImpl implements TablesDAO {


    public TablesImpl(Session db) {
        super(db);
    }

    @Override
    public List<Table> getTables() {
        try {
            return db.query(Table.class).all();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Table getTableById(int id) {
        try {
            return db.query(Table.class).eq("id", id).first();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Table getTableByName(String name) {
        try {
            return db.query(Table.class).eq("name", name).first();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Table getTableByUid(int uid) {
        try {
            return db.query(Table.class).eq("uid", uid).first();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Table createTable(Table table) {
        try {
            db.insert(table);
            table = db.query(Table.class).eq("name", table.name).first();
            return table;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Table updateTable(Table table) {
        try {
            // 判断是否存在
            db.query(Table.class).eq("id", table.id).first();
            db.update(table);
            return db.query(Table.class).eq("id", table.id).first();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTable(int id) {
        try {
            db.delete(Table.class).eq("id", id).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
