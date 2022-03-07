package top.easyblog.titan.redis;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * Redis key过期事件的监听
 *
 * @author: frank.huang
 * @date: 2022-03-06 22:31
 */
@Slf4j
public abstract class AbstractRedisKeyExpireListener extends KeyExpirationEventMessageListener {

    public AbstractRedisKeyExpireListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 处理key过期事件
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {
        try {
            log.info("Start process redis key `{}` expire event.", message);
            doTask(message);
            log.info("Process redis key `{}` expire event successfully!", message);
        } catch (Exception e) {
            log.info("Process redis key expire event failed,case:{}", e.getMessage());
        }
    }


    /**
     * 处理key过期事件
     *
     * @param message
     */
    public abstract void doTask(Message message);
}
