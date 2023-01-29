package top.easyblog.titan.handler;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.response.BaseResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: frank.huang
 * @date: 2021-12-05 10:35
 */
@RestController
public class ServletErrorController implements ErrorController {

    @ResponseWrapper
    @RequestMapping("/error")
    public BaseResponse<Object> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        return BaseResponse.fail(statusCode.toString(), HttpStatus.valueOf(statusCode).name()+(exception == null ? ":" : ":"+exception.getMessage()));
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
