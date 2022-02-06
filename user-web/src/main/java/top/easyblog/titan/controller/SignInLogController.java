package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.QuerySignInLogListRequest;
import top.easyblog.titan.request.QuerySignInLogRequest;
import top.easyblog.titan.service.impl.UserSignInLogService;

/**
 * @author frank.huang
 * @date 2022/02/03 18:21
 */
@RestController
@RequestMapping("/v1/in/sign-log")
public class SignInLogController {

    @Autowired
    private UserSignInLogService userSignInLogService;

    @ResponseWrapper
    @GetMapping
    public Object query(@Valid QuerySignInLogRequest request) {
        return userSignInLogService.querySignInLogDetails(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@Valid QuerySignInLogListRequest request) {
        return userSignInLogService.querySignInLogList(request);
    }

}
