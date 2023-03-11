package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.LogoutRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.service.ILoginService;

import javax.validation.Valid;

/**
 * 用户登录注册认证控制器
 *
 * @author frank.huang
 * @date 2022/01/29 15:44
 */
@RestController
@RequestMapping("/v1/auth")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    /**
     * 生成6位验证码（纯数字）
     *
     * @return
     */
    @ResponseWrapper
    @GetMapping("/captcha-code")
    public void sendCaptchaCode(@RequestParam("identifier_type") Integer identifierType,
                                @RequestParam("identifier") String identifier) {
        loginService.sendCaptchaCode(identifierType, identifier);
    }

    @ResponseWrapper
    @PostMapping("/login")
    public Object login(@RequestBody @Valid LoginRequest request) {
        if (IdentifierType.THIRD_IDENTITY_TYPE.contains(IdentifierType.subCodeOf(request.getIdentifierType()))) {
            throw new BusinessException(ZeusResultCode.INVALID_IDENTITY_TYPE);
        }
        return loginService.login(request);
    }

    @ResponseWrapper
    @GetMapping("/health")
    public Object validateLoginStatus(@RequestBody @Valid LogoutRequest request) {
        return loginService.checkLoginHealth(request);
    }

    @ResponseWrapper
    @PostMapping("/logout")
    public Boolean logout(@RequestBody LogoutRequest request) {
        return loginService.logout(request);
    }

    @ResponseWrapper
    @PostMapping("/register")
    public Object register(@RequestBody @Valid RegisterUserRequest request) {
        if (IdentifierType.THIRD_IDENTITY_TYPE.contains(IdentifierType.subCodeOf(request.getIdentifierType()))) {
            throw new BusinessException(ZeusResultCode.INVALID_IDENTITY_TYPE);
        }
        return loginService.register(request);
    }

}
