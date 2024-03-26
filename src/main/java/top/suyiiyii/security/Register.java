package top.suyiiyii.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.Su.orm.core.Session;
import top.suyiiyii.dao.Users;
import top.suyiiyii.dao.UsersImpl;
import top.suyiiyii.models.User;

public class Register {
    private static final Log logger = LogFactory.getLog(Register.class);

    public static User register(Session db, String username, String password) {
        logger.info("用户注册：" + username);
        Users users = new UsersImpl(db);
        if (username == null || password == null) {
            logger.error("用户注册失败，参数错误");
            logger.error("username: " + username);
            logger.error("password: " + password);
            return null;
        }
        User user = new User();
        user.username = username;
        user.password = top.suyiiyii.security.Login.hashPassword(password);

        user = users.createUser(user);
        logger.info("用户注册成功：" + username);
        return user;
    }
}
