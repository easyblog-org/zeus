package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.service.ILoginService;

/**
 * 系统账号（系统账号、邮箱号、手机号）用户登录注册认证控制器
 *
 * @author frank.huang
 * @date 2022/01/29 15:44
 */
@RestController
@RequestMapping("/v1/in/sys-user")
public class SysUserLoginController {

    @Autowired
    private ILoginService loginService;

    @ResponseWrapper
    @PostMapping
    public Object login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }


}
