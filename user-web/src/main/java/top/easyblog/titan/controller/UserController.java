package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public Object query(@Valid QueryUserRequest request) {
        return userService.queryUserDetails(request);
    }

    @ResponseWrapper
    @PutMapping
    public void update(@RequestBody @Valid UpdateUserRequest request) {
        userService.updateUser(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@Valid QueryUsersRequest request) {
        return userService.queryUserListPage(request);
    }

    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreateUserRequest request) {
        userService.createUser(request);
    }
}
