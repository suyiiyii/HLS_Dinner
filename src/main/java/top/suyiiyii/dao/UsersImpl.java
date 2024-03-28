package top.suyiiyii.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.models.User;
import top.suyiiyii.su.orm.core.Session;

import java.util.NoSuchElementException;

/**
 * UsersImpl UserDAO的实现
 * 用于操作用户表
 *
 * @author suyiiyii
 */
public class UsersImpl implements UsersDAO {
    private final Session db;
    Log logger = LogFactory.getLog(UsersImpl.class);

    public UsersImpl(Session db) {
        this.db = db;
    }

    /**
     * 根据id获取用户
     *
     * @param id 用户id
     * @return 用户
     */
    @Override
    public User getUserById(int id) {
        logger.debug("getUserById: " + id);
        User user = null;
        try {
            user = db.query(User.class).eq("id", id).first();
        } catch (Exception e) {
            logger.warn(e);
        }
        if (user == null) {
            throw new NoSuchElementException("用户不存在");
        }
        return user;
    }

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     * @throws NoSuchElementException 用户不存在
     */
    @Override
    public User getUserByUsername(String username) throws NoSuchElementException {
        User user = null;
        try {
            user = db.query(User.class).eq("username", username).first();
        } catch (Exception e) {
            logger.warn(e);
        }
        if (user == null) {
            throw new NoSuchElementException("用户不存在");
        }
        return user;
    }

    /**
     * 创建用户
     *
     * @param user 用户
     * @return 用户
     */
    @Override
    public User createUser(User user) {
        try {
            db.insert(user);
        } catch (Exception e) {
            logger.warn(e);
        }
        return user;
    }
}
