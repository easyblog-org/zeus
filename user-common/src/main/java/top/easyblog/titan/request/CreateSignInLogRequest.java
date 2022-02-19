package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2022-02-10 21:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSignInLogRequest {
    /**
     * 关联用户id
     */
    private Long userId;
    /**
     * 登录token
     */
    private String token;
    /**
     * 登录状态
     */
    private Integer status;
    /**
     * 登录IP
     */
    private String ip;
    /**
     * 登录设备名称
     */
    private String device;
    /**
     * 登录设备操作系统
     */
    private String operationSystem;
    /**
     * 登录物理定位
     */
    private String location;
}
