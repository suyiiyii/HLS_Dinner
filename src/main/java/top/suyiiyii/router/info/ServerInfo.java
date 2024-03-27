package top.suyiiyii.router.info;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.dao.Users;
import top.suyiiyii.dao.UsersImpl;
import top.suyiiyii.models.User;
import top.suyiiyii.su.WebUtils;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;

@WebServlet("/server/info")
public class ServerInfo extends BaseHttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users users = new UsersImpl(db);
        User user = users.getUserById(uid);
        WebUtils.respWrite(resp, "Hello " + user.username);

    }
}
