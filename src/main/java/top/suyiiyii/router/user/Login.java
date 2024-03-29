package top.suyiiyii.router.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.schemas.Token;
import top.suyiiyii.schemas.TokenData;
import top.suyiiyii.su.JwtUtils;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;

import static top.suyiiyii.su.WebUtils.respWrite;

/**
 * 用户登录Servlet
 * 登录成功后签发token
 *
 * @author suyiiyii
 */
@WebServlet("/user/login")
public class Login extends BaseHttpServlet {
    static final Log logger = LogFactory.getLog(Login.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        LoginRequest request = new LoginRequest();

        request.grant_type = req.getParameter("grant_type");
        request.username = req.getParameter("username");
        request.password = req.getParameter("password");

        logger.info("用户请求登录：" + request.username);

        Token token = new Token();

        token.token_type = "Bearer";

        TokenData tokenData;

        tokenData = top.suyiiyii.service.security.Login.login(db, request.username, request.password);
        String secret = configManger.get("secret");
        token.access_token = JwtUtils.createToken(tokenData, secret, 60 * 60 * 24);

        logger.info("签发token：" + token.access_token);


        respWrite(resp, token);
    }

}

class LoginRequest {
    public String grant_type;
    public String username;
    public String password;
}

