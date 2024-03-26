package top.suyiiyii.router.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.schemas.Token;
import top.suyiiyii.schemas.TokenData;

import java.io.IOException;
import java.util.Date;

import static top.suyiiyii.Utils.*;

@WebServlet("/user/login")
public class Login extends HttpServlet {
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

        try {
            tokenData = top.suyiiyii.security.Login.login(request.username, request.password);
        } catch (Exception e) {
            logger.error("登录失败：" + e.getMessage());
            resp.setStatus(401);
            respWrite(resp, e.getMessage());
            e.printStackTrace();
            return;
        }


        token.access_token = JWT.create()
                .withSubject(obj2Json(tokenData))
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600))
                .sign(Algorithm.HMAC256("secret"));

        logger.info("签发token：" + token.access_token);


        respWrite(resp, token);
    }

}

class LoginRequest {
    public String grant_type;
    public String username;
    public String password;
}

