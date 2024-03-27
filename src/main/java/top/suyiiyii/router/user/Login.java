package top.suyiiyii.router.user;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.su.ConfigManger;
import top.suyiiyii.su.JwtUtils;
import top.suyiiyii.su.orm.core.Session;
import top.suyiiyii.schemas.Token;
import top.suyiiyii.schemas.TokenData;

import java.io.IOException;

import static top.suyiiyii.su.orm.WebUtils.getConfigMangerFromConfig;
import static top.suyiiyii.su.orm.WebUtils.getSessionFromConfig;
import static top.suyiiyii.Utils.respWrite;

@WebServlet("/user/login")
public class Login extends HttpServlet {
    static final Log logger = LogFactory.getLog(Login.class);
    Session db;
    ConfigManger config;

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

        try {
            tokenData = top.suyiiyii.security.Login.login(db, request.username, request.password);
        } catch (Exception e) {
            logger.error("登录失败：" + e.getMessage());
            resp.setStatus(401);
            respWrite(resp, e.getMessage());
            e.printStackTrace();
            return;
        }

        String secret = config.get("secret");
        token.access_token = JwtUtils.createToken(request.username, secret, 60 * 10);

        logger.info("签发token：" + token.access_token);


        respWrite(resp, token);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.db = getSessionFromConfig(config);
        this.config = getConfigMangerFromConfig(config);
    }

}

class LoginRequest {
    public String grant_type;
    public String username;
    public String password;
}

