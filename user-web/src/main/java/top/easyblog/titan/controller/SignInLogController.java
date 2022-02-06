package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.easyblog.titan.service.impl.SignInLogService;

/**
 * @author frank.huang
 * @date 2022/02/03 18:21
 */
@RestController
@RequestMapping("/v1/in/sign-log")
public class SignInLogController {

    @Autowired
    private SignInLogService signInLogService;

}
