package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.RequestParamAlias;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.bean.UserHeaderImgBean;
import top.easyblog.titan.request.CreateUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgsRequest;
import top.easyblog.titan.response.PageResponse;
import top.easyblog.titan.service.UserHeaderImgService;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2022-02-10 20:42
 */
@RestController
@RequestMapping("/v1/in/header-img")
public class UserHeaderImgController {

    @Autowired
    private UserHeaderImgService headerImgService;

    @ResponseWrapper
    @PostMapping
    public void save(@Valid @RequestBody CreateUserHeaderImgRequest request) {
        headerImgService.createUserHeaderImg(request);
    }

    @ResponseWrapper
    @GetMapping
    public Object query(@Valid @RequestParamAlias QueryUserHeaderImgRequest request) {
        return headerImgService.queryUserHeaderDetails(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public PageResponse<UserHeaderImgBean> queryList(@Valid @RequestParamAlias QueryUserHeaderImgsRequest request) {
        return headerImgService.queryUserHeaderList(request);
    }
}
