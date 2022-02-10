package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.QueryUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgsRequest;
import top.easyblog.titan.service.impl.UserHeaderImgService;

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
    @GetMapping
    public Object query(@Valid QueryUserHeaderImgRequest request) {
        return headerImgService.queryUserHeaderDetails(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@Valid QueryUserHeaderImgsRequest request) {
        return headerImgService.queryUserHeaderList(request);
    }
}
