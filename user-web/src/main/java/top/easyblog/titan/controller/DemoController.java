package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.request.CreateUserRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.service.IDemoService;

import java.util.List;

/**
 * Example controller
 *
 * @author: frank.huang
 * @date: 2021-10-31 23:08
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/demo")
public class DemoController {

    @Autowired
    private IDemoService demoService;

    /**
     * 没有返回值的controller
     */
    @ResponseWrapper
    @GetMapping("/empty")
    public void empty() {

    }

    /**
     * 返回string类型的controller
     *
     * @return
     */
    @ResponseWrapper
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ResponseWrapper
    @GetMapping("/int")
    public Integer integer() {
        return demoService.demo1();
    }

    /**
     * 返回类型是对象
     *
     * @return
     */
    @ResponseWrapper
    @GetMapping("/object")
    public UserDetailsBean object(QueryUserRequest request) {
        return new UserDetailsBean();
    }

    /**
     * 返回list的controller
     *
     * @return
     */
    @ResponseWrapper
    @GetMapping("/list")
    public List<UserDetailsBean> list() {
        return demoService.demo3();
    }

    /**
     * 测试post请求返回类型
     *
     * @param request
     * @return
     */
    @ResponseWrapper
    @PostMapping("/add")
    public CreateUserRequest postObject(@RequestBody CreateUserRequest request) {
        return request;
    }

    /**
     * 测试异常处理
     */
    @ResponseWrapper
    @GetMapping("/exp/{num}")
    public void exception(@PathVariable(value = "num") String num) {
        System.out.println(num);
        throw new ArithmeticException();
    }

    @ResponseWrapper
    @GetMapping(value = "/demo4")
    public Object fetchBaidu(QueryUserRequest request){
        return demoService.demo4(request);
    }

}
