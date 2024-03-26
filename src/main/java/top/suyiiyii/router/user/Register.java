package top.suyiiyii.router.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static top.suyiiyii.Utils.readRequestBody2Obj;
import static top.suyiiyii.Utils.respWrite;

import top.suyiiyii.models.User;

@WebServlet("/user/register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        RegisterRequest registerRequest = readRequestBody2Obj(request, RegisterRequest.class);
        User user = new User();
        user = top.suyiiyii.security.Register.register(registerRequest.username, registerRequest.password);
        respWrite(response, user);
    }
}

class RegisterRequest {
    public String username;
    public String password;
}