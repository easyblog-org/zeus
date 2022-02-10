package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.CreateUserRequest;
import top.easyblog.titan.request.QueryUserListRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.request.UpdateUserRequest;
import top.easyblog.titan.service.impl.UserService;

import javax.validation.Valid;

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
    @PutMapping("/{user_id}")
    public void update(@PathVariable("user_id") Long userId,
                       @RequestBody @Valid UpdateUserRequest request) {
        request.setId(userId);
        userService.updateUser(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@Valid QueryUserListRequest request) {
        return userService.queryUserListPage(request);
    }

    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreateUserRequest request) {
        userService.createUser(request);
    }
}
