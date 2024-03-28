package top.suyiiyii.dao;

/**
 * BaseDAO 接口
 * 所有DAO接口的基类
 * 添加几个基本的事务操作
 */
public interface BaseDAO {
    /**
     * 开启事务
     */
    void beginTransaction();

    /**
     * 提交事务
     */
    void commitTransaction();

    /**
     * 回滚事务
     */
    void rollbackTransaction();
}
