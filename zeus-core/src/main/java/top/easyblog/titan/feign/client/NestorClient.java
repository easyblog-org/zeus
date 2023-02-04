package top.easyblog.titan.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.easyblog.titan.feign.client.dto.MessageSendResultDTO;
import top.easyblog.titan.feign.config.CommonFeignConfig;
import top.easyblog.titan.feign.internal.BaseClientResponse;
import top.easyblog.titan.feign.internal.Verify;
import top.easyblog.titan.request.NoticeSendRequest;

/**
 * 通知服务：发送短信/邮件/微信...通知
 *
 * @author: frank.huang
 * @date: 2023-02-03 21:47
 */
@FeignClient(name = "nestor", url = "${urls.nestor}", configuration = CommonFeignConfig.class)
public interface NestorClient extends Verify {

    /**
     * 发送消息
     *
     * @return
     */
    @PostMapping("/")
    BaseClientResponse<MessageSendResultDTO> sendNotice(@RequestBody NoticeSendRequest request);

}
