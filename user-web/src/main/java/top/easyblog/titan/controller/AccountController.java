package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.CreateAccountRequest;
import top.easyblog.titan.request.QueryAccountListRequest;
import top.easyblog.titan.request.QueryAccountRequest;
import top.easyblog.titan.request.UpdateAccountRequest;
import top.easyblog.titan.service.impl.UserAccountService;

import javax.validation.Valid;

/**
 * @author frank.huang
 * @date 2022/02/06 10:32
 */
@RestController
@RequestMapping("/v1/in/account")
public class AccountController {

    @Autowired
    private UserAccountService accountService;


    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreateAccountRequest request) {
        accountService.createAccount(request);
    }

    @ResponseWrapper
    @GetMapping
    public Object query(@Valid QueryAccountRequest request) {
        return accountService.queryAccountDetails(request);
    }

    @ResponseWrapper
    @PutMapping("/{account_id}")
    public void update(@PathVariable("account_id") Long accountId,
                       @RequestBody @Valid UpdateAccountRequest request) {
        accountService.updateAccount(request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@Valid QueryAccountListRequest request) {
        return accountService.queryAccountList(request);
    }
}
