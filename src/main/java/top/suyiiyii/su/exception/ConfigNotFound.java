package top.suyiiyii.su.exception;

/**
 * 找不到配置文件异常
 *
 * @author suyiiyii
 */
public class ConfigNotFound extends RuntimeException {
    public ConfigNotFound(String message) {
        super(message);
    }
}
