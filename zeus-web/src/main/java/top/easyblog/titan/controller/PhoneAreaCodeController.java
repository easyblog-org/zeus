package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.RequestParamAlias;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.CreatePhoneAreaCodeRequest;
import top.easyblog.titan.request.QueryPhoneAreaCodeListRequest;
import top.easyblog.titan.request.QueryPhoneAreaCodeRequest;
import top.easyblog.titan.request.UpdatePhoneAreaCodeRequest;
import top.easyblog.titan.service.PhoneAreaCodeService;

import javax.validation.Valid;

/**
 * @author: frank.huang
 * @date: 2022-02-10 20:51
 */
@RestController
@RequestMapping("/v1/in/area-code")
public class PhoneAreaCodeController {

    @Autowired
    private PhoneAreaCodeService phoneAreaCodeService;


    @ResponseWrapper
    @GetMapping
    public Object query(@Valid @RequestParamAlias QueryPhoneAreaCodeRequest request) {
        return phoneAreaCodeService.queryPhoneAreaCodeDetails(request);
    }

    @ResponseWrapper
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long areaCodeId,
                       @RequestBody @Valid UpdatePhoneAreaCodeRequest request) {
        phoneAreaCodeService.updatePhoneAreaCode(areaCodeId, request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@Valid @RequestParamAlias QueryPhoneAreaCodeListRequest request) {
        return phoneAreaCodeService.queryPhoneAreaCodePage(request);
    }

    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreatePhoneAreaCodeRequest request) {
        phoneAreaCodeService.createPhoneAreaCode(request);
    }

}
