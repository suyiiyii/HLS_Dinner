package top.suyiiyii.service;

import top.suyiiyii.dao.TablesDAO;
import top.suyiiyii.dao.TablesImpl;
import top.suyiiyii.models.Table;
import top.suyiiyii.su.orm.core.Session;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有关桌子的业务逻辑
 */
public class TableService {
    private static final Lock lock = new ReentrantLock();
    TablesDAO tablesDAO;

    public TableService(Session db) {
        tablesDAO = new TablesImpl(db);
    }

    /**
     * 获取所有桌子的信息
     */
    public List<Table> getTables() {
        return this.tablesDAO.getTables();
    }

    /**
     * 通过id获取一张桌子的信息
     */
    public Table getTableById(int id) {
        try {
            return this.tablesDAO.getTableById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Table not found");
        }
    }

    /**
     * 通过name获取一张桌子的信息
     */
    public Table getTableByName(String name) {
        try {
            return this.tablesDAO.getTableByName(name);
        } catch (RuntimeException e) {
            throw new RuntimeException("Table not found");
        }
    }

    /**
     * 通过uid获取一张桌子的信息
     */
    public Table getTableByUid(int uid) {
        try {
            return this.tablesDAO.getTableByUid(uid);
        } catch (RuntimeException e) {
            throw new RuntimeException("Table not found");
        }
    }

    /**
     * 注册一张桌子
     * 用户使用的接口
     */
    public void registerTable(int id, int uid) {
        lock.lock();
        tablesDAO.beginTransaction();
        try {
            // 检查桌子是否已被占用
            Table table = getTableById(id);
            if (table.status.equals("已占用")) {
                throw new RuntimeException("Table already in use");
            }
            // 检查用户是否已注册过
            boolean isExist = true;
            try {
                tablesDAO.getTableByUid(uid);
            } catch (RuntimeException e) {
                isExist = false;
            }
            if (isExist) {
                throw new RuntimeException("User already registered");
            }

            table.status = "已占用";
            table.registerTime = (int) (System.currentTimeMillis() / 1000);
            table.uid = uid;
            tablesDAO.updateTable(table);
            tablesDAO.commitTransaction();
        } catch (RuntimeException e) {
            tablesDAO.rollbackTransaction();
            throw new RuntimeException(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    /**
     * 释放一张桌子
     */
    public void releaseTable(int id, int uid) {
        tablesDAO.beginTransaction();
        Table table = getTableById(id);
        // 判断是否是该用户注册的桌子
        if (table.uid != uid) {
            throw new RuntimeException("Table not registered by this user");
        }

        table.status = "空闲";
        table.registerTime = -1;
        table.uid = -1;
        tablesDAO.updateTable(table);
        tablesDAO.commitTransaction();
    }

    /**
     * 创建一张桌子
     */
    public void createTable(String name, String description) {
        Table table = new Table();
        table.name = name;
        table.description = description;
        boolean isExist = false;
        // 检查是否已存在同样的name
        try {
            getTableByName(name);
        } catch (RuntimeException e) {
            isExist = true;
        }
        if (!isExist) {
            throw new RuntimeException("Table already exists");
        }
        // 赋初始值
        table.status = "空闲";
        table.registerTime = -1;
        table.uid = -1;
        tablesDAO.createTable(table);
    }

    public void updateTable(Table table) {
        // 检查待更新表是否存在
        try {
            getTableById(table.id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Table not found");
        }
        // 更新表
        Table oldTable = getTableById(table.id);
        if (table.name == null) {
            table.name = oldTable.name;
        }
        if (table.description == null) {
            table.description = oldTable.description;
        }
        if (table.status == null) {
            table.status = oldTable.status;
        }
        if (table.registerTime == -1) {
            table.registerTime = oldTable.registerTime;
        }
        if (table.uid == -1) {
            table.uid = oldTable.uid;
        }
        if (table.imgUrl == null) {
            table.imgUrl = oldTable.imgUrl;
        }
        // TODO: 添加使用反射，用一个对象的非空属性更新另一个对象
        tablesDAO.updateTable(table);
    }

    /**
     * 删除一张桌子
     *
     * @param id 桌子id
     */
    public void deleteTable(int id) {
        // 检查待删除表是否存在
        try {
            getTableById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Table not found");
        }
        // 删除表
        tablesDAO.deleteTable(id);
    }

}
