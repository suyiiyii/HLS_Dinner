package top.suyiiyii.router.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.models.User;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;

import static top.suyiiyii.su.WebUtils.readRequestBody2Obj;
import static top.suyiiyii.su.WebUtils.respWrite;

/**
 * 注册Servlet
 *
 * @author suyiiyii
 */
@WebServlet("/user/register")
public class Register extends BaseHttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RegisterRequest registerRequest = readRequestBody2Obj(request, RegisterRequest.class);
        User user = top.suyiiyii.security.Register.register(this.db, registerRequest.username, registerRequest.password);
        respWrite(response, user);
    }
}

class RegisterRequest {
    public String username;
    public String password;
}