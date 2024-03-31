package top.suyiiyii.router.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.su.WebUtils;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;

import static top.suyiiyii.service.security.Login.changePassword;

@WebServlet("/user/changepwd")
public class Changepwd extends BaseHttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChangePwdReq req1 = WebUtils.readRequestBody2Obj(req, ChangePwdReq.class);
        if (req1.oldPassword.equals(req1.newPassword)) {
            throw new ServletException("新旧密码不能相同");
        }
        changePassword(db, uid, req1.oldPassword, req1.newPassword);
        WebUtils.respWrite(resp, "修改成功");
    }

    public static class ChangePwdReq {
        public String oldPassword;
        public String newPassword;
    }
}
