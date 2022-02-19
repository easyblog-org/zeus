package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.LogoutRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.service.auth.ILoginService;

import javax.validation.Valid;

/**
 * 用户登录注册认证控制器
 *
 * @author frank.huang
 * @date 2022/01/29 15:44
 */
@RestController
@RequestMapping("/v1/in/auth")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @ResponseWrapper
    @PostMapping("/login")
    public Object login(@RequestBody @Valid LoginRequest request) {
        return loginService.login(request);
    }

    @ResponseWrapper
    @GetMapping("/health")
    public Object validateLoginStatus(@RequestParam("token") String token) {
        return loginService.checkLoginHealth(token);
    }

    @ResponseWrapper
    @PostMapping("/logout")
    public void logout(@RequestBody @Valid LogoutRequest request) {
        loginService.logout(request);
    }

    @ResponseWrapper
    @PostMapping("/register")
    public Object register(@RequestBody @Valid RegisterUserRequest request) {
        return loginService.register(request);
    }

}
