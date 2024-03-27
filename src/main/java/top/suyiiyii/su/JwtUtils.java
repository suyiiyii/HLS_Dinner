package top.suyiiyii.su;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /**
     * 创建token
     *
     * @param data    数据
     * @param secret  密钥
     * @param expTime 过期时间
     * @return token
     * @throws IOException 异常
     */
    public static String createToken(Object data, String secret, int expTime) throws IOException {
        Map<String, String> payload = new HashMap<>();
        payload.put("sub", UniversalUtils.obj2Json(data));
        long exp = System.currentTimeMillis() + expTime;
        payload.put("exp", String.valueOf(exp));
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create().withPayload(payload).sign(algorithm);
            return token;
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
            throw new IOException("verify token failed");
        }
    }
}
