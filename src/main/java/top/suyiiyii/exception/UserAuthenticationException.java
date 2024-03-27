package top.suyiiyii.exception;

/**
 * 用户认证异常
 * 当用户认证失败时抛出
 *
 * @author suyiiyii
 */
public class UserAuthenticationException extends Exception {
    public UserAuthenticationException(String message) {
        super(message);
    }
}
