package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.RequestParamAlias;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.request.*;
import top.easyblog.titan.service.RolesService;

import javax.validation.Valid;

/**
 * @author frank.huang
 * @date 2022/02/03 18:20
 */
@RestController
@RequestMapping("/v1/in/roles")
public class RoleController {

    @Autowired
    private RolesService userRolesService;

    @ResponseWrapper
    @GetMapping
    public Object query(@Valid @RequestParamAlias QueryRolesDetailsRequest request) {
        return userRolesService.details(request);
    }

    @ResponseWrapper
    @PutMapping("/{code}")
    public void update(@PathVariable("code") String code,
                       @RequestBody @Valid UpdateRolesRequest request) {
        userRolesService.updateRoles(code,request);
    }

    @ResponseWrapper
    @GetMapping("/list")
    public Object queryList(@RequestParamAlias QueryRolesListRequest request) {
        return userRolesService.queryRolesList(request);
    }

    @ResponseWrapper
    @PostMapping
    public void create(@RequestBody @Valid CreateRolesRequest request) {
        userRolesService.create(request);
    }
}
