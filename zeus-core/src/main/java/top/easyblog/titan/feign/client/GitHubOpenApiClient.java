package top.easyblog.titan.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import top.easyblog.titan.feign.client.dto.GitHubAuthDTO;
import top.easyblog.titan.feign.config.CommonFeignConfig;
import top.easyblog.titan.feign.internal.Verify;

/**
 * @author: frank.huang
 * @date: 2022-02-27 12:10
 */
@FeignClient(name = "github-open-api", url = "${urls.github-api}", configuration = CommonFeignConfig.class)
public interface GitHubOpenApiClient extends Verify {

    /**
     * 获取GitHub授权用户信息
     *
     * @param token
     * @return
     */
    @GetMapping(value = "/user")
    GitHubAuthDTO getGithubUserInfo(@RequestHeader(name = "Authorization") String token);


}
