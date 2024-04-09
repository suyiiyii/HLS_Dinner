package top.suyiiyii.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import top.suyiiyii.su.ConfigManger;

import java.util.concurrent.TimeUnit;

public class RedissionLock {

    public static RLock lock;
    protected static Log logger = LogFactory.getLog(RedissionLock.class);
    private static String password;
    private static String url;

    public static void init() {
        Config config = new Config();

        ConfigManger configManger = new ConfigManger("application.properties");
        // 如果没有配置url，就不使用分布式锁
        try {
            url = configManger.get("REDIS_URL");
            password = configManger.get("REDIS_PASSWORD");
            config.useSingleServer().setAddress(url);
            config.useSingleServer().setPassword(password);
        } catch (Exception e) {
            url = null;
        }
        if (url != null) {
            logger.info("开始连接Redis");
            RedissonClient redisson = Redisson.create(config);
            lock = redisson.getLock("dinner");
            logger.info("成功连接Redis");
        }
    }

    public static void lock() {
        if (url != null) {
            logger.info("尝试获取锁");
            lock.lock(5, TimeUnit.SECONDS);
            logger.info("成功获取锁");
        }
    }

    public static void unlock() {
        if (url != null) {
            lock.unlock();
            logger.info("成功释放锁");
        }
    }

}
