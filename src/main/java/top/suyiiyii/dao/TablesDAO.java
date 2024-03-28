package top.suyiiyii.dao;

import top.suyiiyii.models.Table;

import java.util.List;

/**
 * TablesDAO DAO 接口
 * 对应为餐馆内的桌子
 *
 * @author suyiiyii
 */
public interface TablesDAO extends BaseDAO{
    /**
     * 获取所有桌子
     *
     * @return 所有桌子
     */
    public List<Table> getTables();

    /**
     * 根据id获取桌子
     *
     * @param id 桌子id
     * @return 桌子
     */
    public Table getTableById(int id);

    /**
     * 根据name获取桌子
     *
     * @param name 桌子name
     * @return 桌子
     */
    public Table getTableByName(String name);

    /**
     * 创建桌子
     *
     * @param table 桌子
     * @return 桌子
     */
    public Table createTable(Table table);

    /**
     * 更新桌子
     *
     * @param table 桌子
     * @return 桌子
     */
    public Table updateTable(Table table);
}
