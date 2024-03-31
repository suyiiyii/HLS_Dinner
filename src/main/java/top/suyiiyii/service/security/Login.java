package top.suyiiyii.service.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.dao.UsersDAO;
import top.suyiiyii.dao.UsersImpl;
import top.suyiiyii.exception.UserAuthenticationException;
import top.suyiiyii.models.User;
import top.suyiiyii.schemas.TokenData;
import top.suyiiyii.su.orm.core.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Login
 * 登录service
 * 验证用户名和密码
 * 返回TokenData
 *
 * @author suyiiyii
 */
public class Login {
    private static final Log logger = LogFactory.getLog(Register.class);

    public static TokenData login(Session db, String username, String password) throws UserAuthenticationException {
        UsersDAO usersDAO = new UsersImpl(db);
        User user = usersDAO.getUserByUsername(username);


        if (!checkPassword(password, user)) {
            throw new UserAuthenticationException("用户名或密码错误");
        }

        TokenData tokenData = new TokenData();
        tokenData.uid = user.id;
        tokenData.role = user.role;
        return tokenData;
    }

    public static void changePassword(Session db,int uid,String oldPassword, String newPassword) {
        UsersDAO usersDAO = new UsersImpl(db);
        User user = usersDAO.getUserById(uid);
        if (!checkPassword(oldPassword, user)) {
            throw new UserAuthenticationException("原密码错误");
        }
        user.password = hashPassword(newPassword);
        usersDAO.updateUser(user);
    }

    public static boolean checkPassword(String password, User user) {
        return user.password.equals(hashPassword(password));
    }

    public static String hashPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("md5算法不可用");
            throw new RuntimeException("md5算法不可用");
        }
        md.update(password.getBytes());
        md.update("suyiiyii".getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
