package top.suyiiyii.dao;

import top.suyiiyii.models.Table;

import java.util.List;

/**
 * TablesDAO DAO 接口
 * 对应为餐馆内的桌子
 *
 * @author suyiiyii
 */
public interface TablesDAO extends BaseDAO {
    /**
     * 获取所有桌子
     *
     * @return 所有桌子
     */
    List<Table> getTables();

    /**
     * 根据id获取桌子
     *
     * @param id 桌子id
     * @return 桌子
     */
    Table getTableById(int id);

    /**
     * 根据name获取桌子
     *
     * @param name 桌子name
     * @return 桌子
     */
    Table getTableByName(String name);

    /**
     * 根据uid获取桌子
     *
     * @param uid 用户id
     * @return 桌子
     */
    Table getTableByUid(int uid);

    /**
     * 创建桌子
     *
     * @param table 桌子
     * @return 桌子
     */
    Table createTable(Table table);

    /**
     * 更新桌子
     *
     * @param table 桌子
     * @return 桌子
     */
    Table updateTable(Table table);

    /**
     * 删除桌子
     *
     * @param id 桌子id
     */
    void deleteTable(int id);

}
