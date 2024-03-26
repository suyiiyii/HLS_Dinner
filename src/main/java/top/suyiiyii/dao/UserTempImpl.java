package top.suyiiyii.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.Su.orm.core.Session;
import top.suyiiyii.models.User;

import java.util.NoSuchElementException;

public class UserTempImpl implements Users {
    Log logger = LogFactory.getLog(UserTempImpl.class);
    private Session db;

    public UserTempImpl(Session db) {
        this.db = db;
    }

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
