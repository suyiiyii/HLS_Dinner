package top.suyiiyii.router.info;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.dao.UsersDAO;
import top.suyiiyii.dao.UsersImpl;
import top.suyiiyii.models.User;
import top.suyiiyii.su.WebUtils;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;

/**
 * 获取问候信息
 * 用于测试
 *
 * @author suyiiyii
 */
@WebServlet("/server/info")
public class ServerInfo extends BaseHttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersDAO usersDAO = new UsersImpl(db);
        User user = usersDAO.getUserById(uid);
        WebUtils.respWrite(resp, "Hello " + user.username);

    }
}
