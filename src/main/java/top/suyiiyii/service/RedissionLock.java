package top.suyiiyii.service;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import top.suyiiyii.su.ConfigManger;

public class RedissionLock {

    public static RLock lock;
    private static String password;

    static {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://redis.k8s-redis.svc.cluster.local:6379");

        ConfigManger configManger = new ConfigManger("application.properties");
        // 如果没有配置密码，就不使用分布式锁
        try {
            password = configManger.get("REDIS_PASSWORD");
        } catch (Exception e) {
            password = null;
        }
        if (password != null) {
            config.useSingleServer().setPassword(password);
        }
        RedissonClient redisson = Redisson.create(config);
        lock = redisson.getLock("lock");
    }

    public static void lock() {
        if (password != null) {
            lock.lock();
        }
    }

    public static void unlock() {
        if (password != null) {
            lock.unlock();
        }
    }

}
