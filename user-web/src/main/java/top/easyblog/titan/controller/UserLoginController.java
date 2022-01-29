package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.service.ILoginService;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录注册认证控制器
 *
 * @author frank.huang
 * @date 2022/01/29 15:44
 */
@RestController
@RequestMapping("/v1/in/user")
public class UserLoginController {

    @Autowired
    private ILoginService loginService;

    @ResponseWrapper
    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        return loginService.login(request, httpServletRequest);
    }

    @GetMapping("/login/health")
    public Object validateLogin(String token) {
        return loginService.checkLoginHealth(token);
    }


}
