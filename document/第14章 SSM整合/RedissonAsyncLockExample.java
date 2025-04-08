import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.CompletableFuture;

public class RedissonAsyncLockExample {

    public static void main(String[] args) {
        // 配置 Redisson 客户端
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        // 获取锁
        RLock lock = redisson.getLock("myLock");

        // 尝试获取锁
        if (lock.tryLock()) {
            try {
                System.out.println("主线程获取锁成功");
                // 异步执行业务逻辑和释放锁
                CompletableFuture.runAsync(() -> {
                    try {
                        // 模拟业务逻辑
                        System.out.println("异步线程开始执行业务逻辑");
                        Thread.sleep(2000);
                        System.out.println("异步线程业务逻辑执行完毕");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 释放锁
                        lock.unlock();
                        System.out.println("异步线程释放锁");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("主线程获取锁失败");
        }

        // 主线程直接返回
        System.out.println("主线程直接返回");

        // 确保在程序结束时关闭 Redisson 客户端
        redisson.shutdown();
    }
}    