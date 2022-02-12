package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.service.ILoginService;

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
    public Object login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        return loginService.login(request, httpServletRequest);
    }

    @ResponseWrapper
    @GetMapping("/health")
    public Object validateLoginStatus(@RequestParam("token") String token) {
        return loginService.checkLoginHealth(token);
    }

    @ResponseWrapper
    @GetMapping("/logout")
    public void logout(@RequestParam("token") String token, HttpServletRequest httpServletRequest) {
        loginService.logout(token, httpServletRequest);
    }

    @ResponseWrapper
    @PostMapping("/register")
    public Object register(@RequestBody RegisterUserRequest request, HttpServletRequest httpServletRequest) {
        return loginService.register(request, httpServletRequest);
    }

}