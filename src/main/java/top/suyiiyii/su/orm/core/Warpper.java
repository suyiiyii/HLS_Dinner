package top.suyiiyii.su.orm.core;

import top.suyiiyii.su.orm.struct.CallBack;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sql的查询条件的构建器
 * 使用回调函数
 * 支持链式调用
 * 调用结束方法后会执行传入的回调函数并返回结果
 * 建议使用lambda表达式的方式传入回调函数
 *
 * @author suyiiyii
 */
public class Warpper {
    /**
     * 线程安全：对象仅作为session的工具类，线程安全同session
     */
    // where条件表
    private final List<String> whereStatement = new ArrayList<>();
    // set条件表
    private final List<String> setStatement = new ArrayList<>();
    // 参数列表
    private final List<Object> params = new ArrayList<>();
    private final CallBack callBack;
    private final Class<?> clazz;
    // 分页信息
    private int pageNum = -1;
    private int pageSize = -1;

    public Warpper(Class<?> clazz, CallBack callBack) {
        this.clazz = clazz;
        this.callBack = callBack;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    // 相等条件
    public Warpper eq(String key, Object value) {
        whereStatement.add(key + " = ?");
        params.add(value);
        return this;
    }

    // 不等条件
    public Warpper neq(String key, Object value) {
        whereStatement.add(key + " != ?");
        params.add(value);
        return this;
    }

    // 全字匹配
    public Warpper like(String key, Object value) {
        whereStatement.add(key + " LIKE ?");
        params.add(value);
        return this;
    }

    // 模糊匹配
    public Warpper fuzzLike(String key, String value) {
        whereStatement.add(key + " LIKE ?");
        params.add("%" + value + "%");
        return this;
    }

    // 分页
    public Warpper limit(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        return this;
    }

    // SET
    public Warpper set(String key, Object value) {
        setStatement.add(key + " = ?");
        params.add(value);
        return this;
    }


    public String buildSql(String baseSql) {
        StringBuilder sql = new StringBuilder(baseSql);
        if (!setStatement.isEmpty()) {
            sql.append(" SET ");
            sql.append(String.join(",", setStatement));
        }

        if (!whereStatement.isEmpty()) {
            sql.append(" WHERE ");
            sql.append(String.join(" AND ", whereStatement));
        }


        if (pageNum != -1 && pageSize != -1) {
            sql.append(" LIMIT " + (pageNum - 1) * pageSize + "," + pageSize);
        }

        return sql.toString();
    }


    public PreparedStatement fillParams(PreparedStatement ps) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
        return ps;
    }

    public void execute() throws SQLException {
        callBack.call(this);
    }

    public <T> List<T> all() throws SQLException {
        return (List<T>) callBack.call(this);
    }

    public <T> T first() throws SQLException {
        pageNum = 1;
        pageSize = 1;
        return (T) all().get(0);
    }

}