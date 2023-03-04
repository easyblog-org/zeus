package top.easyblog.titan.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import top.easyblog.titan.enums.LoginStatus;
import top.easyblog.titan.service.atomic.AtomicSignInLogService;

/**
 * @author: frank.huang
 * @date: 2022-03-07 21:03
 */
@Slf4j
@Component
public class UserLoginTokenExpireListener extends AbstractRedisKeyExpireListener {

    @Autowired
    private AtomicSignInLogService signInLogService;

    private final static String LISTEN_KEY = "auth:token";

    public UserLoginTokenExpireListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void doTask(Message message) {
        String expireKey = message.toString();
        if (StringUtils.isNotBlank(expireKey) && expireKey.contains(LISTEN_KEY)) {
            signInLogService.updateSignInLogByToken(expireKey, LoginStatus.OFFLINE.getCode());
            log.info("Process expire token {} event  successfully!", expireKey);
        }
    }
}
