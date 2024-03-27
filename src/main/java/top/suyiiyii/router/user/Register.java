package top.suyiiyii.router.user;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.models.User;
import top.suyiiyii.su.ConfigManger;
import top.suyiiyii.su.orm.core.Session;

import java.io.IOException;

import static top.suyiiyii.su.WebUtils.readRequestBody2Obj;
import static top.suyiiyii.su.WebUtils.respWrite;
import static top.suyiiyii.su.orm.WebUtils.getConfigMangerFromConfig;
import static top.suyiiyii.su.orm.WebUtils.getSessionFromConfig;

/**
 * 注册Servlet
 *
 * @author suyiiyii
 */
@WebServlet("/user/register")
public class Register extends HttpServlet {
    Session db;
    ConfigManger config;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RegisterRequest registerRequest = readRequestBody2Obj(request, RegisterRequest.class);
        User user = new User();
        user = top.suyiiyii.security.Register.register(db, registerRequest.username, registerRequest.password);
        respWrite(response, user);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.db = getSessionFromConfig(config);
        this.config = getConfigMangerFromConfig(config);
    }
}

class RegisterRequest {
    public String username;
    public String password;
}