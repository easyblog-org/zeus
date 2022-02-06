package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.CreateUserRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.request.QueryUsersRequest;
import top.easyblog.titan.request.UpdateUserRequest;
import top.easyblog.titan.service.impl.UserService;

/**
 * @author frank.huang
 * @date 2022/02/03 18:20
 */
@RestController
@RequestMapping("/v1/in/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ResponseWrapper
    @GetMapping
    public Object query(QueryUserRequest request) {
        return userService.queryUserByRequest(request);
    }

    @ResponseWrapper
    @PutMapping
    public void update(UpdateUserRequest request) {
        userService.updateUserByRequest(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(QueryUsersRequest request) {
        return userService.queryUserListPage(request);
    }

    @ResponseWrapper
    @PostMapping
    public void create(CreateUserRequest request) {
        userService.createByRequest(request);
    }
}
