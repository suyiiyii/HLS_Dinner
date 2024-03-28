package top.suyiiyii.service.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.dao.UsersDAO;
import top.suyiiyii.dao.UsersImpl;
import top.suyiiyii.models.User;
import top.suyiiyii.su.orm.core.Session;

import java.util.NoSuchElementException;

/**
 * 注册Service
 *
 * @author suyiiyii
 */
public class Register {
    private static final Log logger = LogFactory.getLog(Register.class);

    public static User register(Session db, String username, String password) {
        logger.info("用户注册：" + username);
        // 参数校验
        UsersDAO usersDAO = new UsersImpl(db);
        if (username == null || password == null) {
            logger.error("用户注册失败，参数错误");
            logger.error("username: " + username);
            logger.error("password: " + password);
            throw new IllegalArgumentException("参数错误");
        }
        User checkUser = null;

        try {
            checkUser = usersDAO.getUserByUsername(username);
        } catch (NoSuchElementException ignored) {
        }

        // 检查用户是否已存在
        if (checkUser != null) {
            logger.error("用户注册失败，用户已存在：" + username);
            throw new IllegalArgumentException("用户已存在");
        }


        User user = new User();
        user.username = username;
        user.password = Login.hashPassword(password);
        user.role = "user";

        user = usersDAO.createUser(user);
        user = usersDAO.getUserByUsername(username);
        logger.info("用户注册成功：" + username);
        return user;
    }
}
