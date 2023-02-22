package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.RequestParamAlias;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.CreateUserRequest;
import top.easyblog.titan.request.QueryUserListRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.request.UpdateUserRequest;
import top.easyblog.titan.service.UserService;

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
    public Object query(@Valid @RequestParamAlias QueryUserRequest request) {
        return userService.queryUserDetails(request);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public Long update(@PathVariable("code") String code,
                       @RequestBody @Valid UpdateUserRequest request) {
        return userService.updateUser(code,request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@RequestParamAlias QueryUserListRequest request) {
        return userService.queryUserListPage(request);
    }

    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreateUserRequest request) {
        userService.createUser(request);
    }
}
