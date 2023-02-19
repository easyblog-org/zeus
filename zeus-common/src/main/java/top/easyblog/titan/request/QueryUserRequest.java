package top.easyblog.titan.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2022-01-29 21:48
 */
@Data
@Builder
public class QueryUserRequest {
    private Long id;
    private String code;
    private String nickName;
    /**
     * 查询选项，默认只查询用户表里的基础信息，分表信息小查询则应该明确在请求参数中指定
     * 可选参数有：header、accounts、sign_log 分别标识需要查询哪些数据
     */
    private String sections;
}
