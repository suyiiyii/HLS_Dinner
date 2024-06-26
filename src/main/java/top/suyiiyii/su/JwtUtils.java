package top.suyiiyii.su;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Date;

/**
 * jwt工具类
 * 用于生成token和验证token
 * 依赖auth0的jwt库
 *
 * @author suyiiyii
 */
public class JwtUtils {
    /**
     * 创建token
     *
     * @param data      数据
     * @param secret    密钥
     * @param expSecond 过期时间
     * @return token
     * @throws IOException 异常
     */
    public static String createToken(Object data, String secret, int expSecond) throws IOException {
        String sub = UniversalUtils.obj2Json(data);
        long exp = System.currentTimeMillis() + expSecond * 1000L;
        Date expDate = new Date(exp);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withSubject(sub).withExpiresAt(expDate).sign(algorithm);
        } catch (Exception e) {
            throw new IOException("create token failed");
        }
    }

    /**
     * 验证token
     *
     * @param token  token
     * @param secret 密钥
     * @return token的sub
     * @throws IOException 异常
     */

    public static String verifyToken(String token, String secret) throws IOException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm).build().verify(token);
            return JWT.decode(token).getClaim("sub").asString();
        } catch (Exception e) {
            throw new AuthenticationException("verify token failed");
        }
    }
}
