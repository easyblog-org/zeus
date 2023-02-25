package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.RequestParamAlias;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.CreateAccountRequest;
import top.easyblog.titan.request.QueryAccountListRequest;
import top.easyblog.titan.request.QueryAccountRequest;
import top.easyblog.titan.request.UpdateAccountRequest;
import top.easyblog.titan.service.AccountService;

import javax.validation.Valid;

/**
 * @author frank.huang
 * @date 2022/02/06 10:32
 */
@RestController
@RequestMapping("/v1/in/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreateAccountRequest request) {
        accountService.createAccount(request);
    }

    @ResponseWrapper
    @GetMapping
    public Object query(@Valid @RequestParamAlias QueryAccountRequest request) {
        return accountService.queryAccountDetails(request);
    }

    @ResponseWrapper
    @PutMapping("/{account_id}")
    public void update(@PathVariable("account_id") Long accountId,
                       @RequestBody @Valid UpdateAccountRequest request) {
        accountService.updateAccount(accountId,request);
    }

    @ResponseWrapper
    @PutMapping("/{user_id}/{identify_type}")
    public void update(@PathVariable("user_id") Long userId,
                       @PathVariable("identify_type") Integer identityType,
                       @RequestBody @Valid UpdateAccountRequest request) {
        accountService.updateByIdentityType(userId,identityType,request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@Valid @RequestParamAlias QueryAccountListRequest request) {
        return accountService.queryAccountList(request);
    }
}
