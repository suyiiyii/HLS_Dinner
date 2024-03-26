package top.suyiiyii.Su.orm.core;

import top.suyiiyii.Su.UniversalUtils;
import top.suyiiyii.Su.orm.struct.Table;
import top.suyiiyii.Su.orm.utils.RowSqlGenerater;
import top.suyiiyii.Su.orm.utils.SqlExecutor;
import top.suyiiyii.Su.orm.utils.SuRowMapper;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * CRUD操作
 * 封装orm框架的CRUD操作
 * 用于与业务层交互
 */


public class Session {
    /**
     * 线程安全：每个线程应有自己的session对象，不保证线程安全
     */
    // 待插入数据暂存区
    private final Map<Class<?>, List<Object>> insertCache = new HashMap<>();
    // 对象备份
    private final Map<Object, Object> cache = new HashMap<>();

    // 归属于的orm对象
    private final ModelManger modelManger;

    private final SqlExecutor sqlExecutor;


    public Session(ModelManger modelManger) {
        this.modelManger = modelManger;
        this.sqlExecutor = modelManger.getConnectionManger().getSqlExecutor();
    }


    /**
     * 查询表的所有信息
     *
     * @param clazz 表对应的类的字节码对象
     * @param <T>   表对应的类
     * @return 包含表的所有信息的列表
     */
    public <T> List<T> selectAll(Class<T> clazz) {
        String tableName = modelManger.getClass2TableName().get(clazz);
        String sql = RowSqlGenerater.selectAll(tableName);
        ResultSet resultSet = sqlExecutor.query(sql);
        return SuRowMapper.RowMapper(clazz, resultSet);
    }


    /**
     * 根据某一个字段查询
     *
     * @param clazz 表对应的类的字节码对象
     * @param key   字段名
     * @param value 字段的值
     * @param <T>   表对应的类
     * @return 包含表的所有信息的列表
     */
    public <T> List<T> selectByKey(Class<T> clazz, String key, Object value) throws SQLException {
        String tableName = modelManger.getClass2TableName().get(clazz);
        String sql = RowSqlGenerater.selectByKey(tableName, key);
        PreparedStatement preparedStatement = sqlExecutor.getPreparedStatement(sql);
        preparedStatement.setObject(1, value);
        ResultSet resultSet = sqlExecutor.query(preparedStatement);
        return SuRowMapper.RowMapper(clazz, resultSet);
    }

    /**
     * 将待插入的对象装入暂存区
     *
     * @param obj 待插入的对象
     * @param <T> 待插入的对象的类型
     */
    public <T> void add(T obj) {
        Class<?> clazz = obj.getClass();
        if (!insertCache.containsKey(clazz)) {
            insertCache.put(clazz, new ArrayList<>());
        }
        // 拷贝一份对象
        T newObj = UniversalUtils.clone(obj);
        insertCache.get(clazz).add(newObj);

    }

    /**
     * 提交事务
     * 提交暂存区的数据
     */
    public void commit() {
        // 提交插入
        for (Map.Entry<Class<?>, List<Object>> entry : insertCache.entrySet()) {
            batchInsert(entry.getValue());
        }
        if (!cache.isEmpty()) {
            checkUpdate();
        }
        // 提交事务
        sqlExecutor.commit();
    }


    /**
     * 回滚事务
     */
    public void rollback() {
        sqlExecutor.rollback();
    }


    /**
     * 批量插入在暂存区内的数据
     *
     * @param list 待插入的对象列表
     * @param <T>  待插入的对象的类型
     */
    public <T> void batchInsert(List<T> list) {
        if (list.isEmpty()) {
            return;
        }
        Class<?> clazz = list.get(0).getClass();
        Table table = modelManger.getClass2Table().get(clazz);
        String sql = RowSqlGenerater.getInsertSql(table);
        try {
            PreparedStatement preparedStatement = sqlExecutor.getPreparedStatement(sql);
            for (T obj : list) {
                // 因为缺少部分字段，所以需要计数器保证字段序号的连续
                int cnt = 1;
                for (int i = 0; i < table.columns.size(); i++) {
                    // 如果是自增字段则跳过
                    if (table.columns.get(i).isAutoIncrement) {
                        continue;
                    }
                    // 获取字段名
                    String fieldName = UniversalUtils.downToCaml(table.columns.get(i).name);
                    // 获取字段的值
                    Field field = obj.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    preparedStatement.setObject(cnt++, field.get(obj));
                }
                preparedStatement.addBatch();
            }
            sqlExecutor.execute(preparedStatement, true);
            list.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入单个对象
     *
     * @param obj 待插入的对象
     * @param <T> 待插入的对象的类型
     */
    public <T> void insert(T obj) {

        Table table = modelManger.getClass2Table().get(obj.getClass());
        String sql = RowSqlGenerater.getInsertSql(table);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = sqlExecutor.getPreparedStatement(sql);
            // 因为缺少部分字段，所以需要计数器保证字段序号的连续
            int cnt = 1;
            for (int i = 0; i < table.columns.size(); i++) {
                // 如果是自增字段则跳过
                if (table.columns.get(i).isAutoIncrement) {
                    continue;
                }
                // 获取字段名
                String fieldName = UniversalUtils.downToCaml(table.columns.get(i).name);
                // 获取字段的值
                Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                preparedStatement.setObject(cnt++, field.get(obj));
            }
            sqlExecutor.execute(preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void update(T obj) {
        Table table = modelManger.getClass2Table().get(obj.getClass());
        String sql = RowSqlGenerater.getUpdateSql(table);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = sqlExecutor.getPreparedStatement(sql);
            // 因为缺少部分字段，所以需要计数器保证字段序号的连续
            int cnt = 1;
            for (int i = 0; i < table.columns.size(); i++) {
                // 如果是主键字段则跳过
                if (table.columns.get(i).isPrimaryKey) {
                    continue;
                }
                // 获取字段名
                String fieldName = UniversalUtils.downToCaml(table.columns.get(i).name);
                // 获取字段的值
                Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                preparedStatement.setObject(cnt++, field.get(obj));
            }
            // 设置主键
            for (int i = 0; i < table.columns.size(); i++) {
                if (table.columns.get(i).isPrimaryKey) {
                    // 获取字段名
                    String fieldName = UniversalUtils.downToCaml(table.columns.get(i).name);
                    // 获取字段的值
                    Field field = obj.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    preparedStatement.setObject(cnt++, field.get(obj));
                    break;
                }
            }
            sqlExecutor.execute(preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询，使用Warpper
     * 支持链式调用添加对象
     * 使用lambda表达式传入回调函数
     *
     * @param clazz 要查询的表
     * @param <T>   要查询的对象的类型
     * @return 查询结果（具体类型取决于链式调用最后执行的方法）
     */
    public <T> Warpper query(Class<T> clazz) {
        return new Warpper(clazz, warpper -> {
            String tableName = modelManger.getClass2TableName().get(warpper.getClazz());
            String sql = warpper.buildSql("SELECT * FROM " + tableName + " ");
            PreparedStatement ps = sqlExecutor.getPreparedStatement(sql);
            ps = warpper.fillParams(ps);
            ResultSet rs = sqlExecutor.query(ps);
            List<T> list = (List<T>) SuRowMapper.RowMapper(warpper.getClazz(), rs);
            for (T obj : list) {
                cache.put(obj, UniversalUtils.clone(obj));
            }
            return list;
        });
    }

    /**
     * 删除，使用Warpper
     * 支持链式调用添加对象
     * 使用lambda表达式传入回调函数
     *
     * @param clazz 要查询的表
     * @param <T>   要查询的对象的类型
     * @return 查询结果（具体类型取决于链式调用最后执行的方法）
     */

    public <T> Warpper delete(Class<T> clazz) {
        return new Warpper(clazz, warpper -> {
            String tableName = modelManger.getClass2TableName().get(warpper.getClazz());
            String sql = warpper.buildSql("DELETE FROM " + tableName + " ");
            PreparedStatement ps = sqlExecutor.getPreparedStatement(sql);
            ps = warpper.fillParams(ps);
            return sqlExecutor.execute(ps);
        });
    }

    /**
     * 更新，使用Warpper
     * 支持链式调用添加对象
     * 使用lambda表达式传入回调函数
     *
     * @param clazz 要查询的表
     * @param <T>   要查询的对象的类型
     * @return 查询结果（具体类型取决于链式调用最后执行的方法）
     */
    public <T> Warpper update(Class<T> clazz) {
        return new Warpper(clazz, warpper -> {
            String tableName = modelManger.getClass2TableName().get(warpper.getClazz());
            String sql = warpper.buildSql("UPDATE " + tableName + " ");
            PreparedStatement ps = sqlExecutor.getPreparedStatement(sql);
            ps = warpper.fillParams(ps);
            return sqlExecutor.execute(ps);
        });
    }

    public void checkUpdate() {
        List<Object> toUpdate = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : cache.entrySet()) {
            Object ori = entry.getValue();
            Object cur = entry.getKey();
            if (!UniversalUtils.equal(ori, cur)) {
                System.out.println("找到一个更改" + cur);
                toUpdate.add(cur);
            }
        }
        cache.clear();
        if (!toUpdate.isEmpty()) {
            for (Object obj : toUpdate) {
//                this.add(obj);
                this.update(obj);
            }
        }

    }

    public void setAutoCommit(boolean autoCommit) {
        sqlExecutor.setAutoCommit(autoCommit);
    }

    public void close() {
        sqlExecutor.close();
    }

}