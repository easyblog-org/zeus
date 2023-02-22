package top.easyblog.titan.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2022-01-29 21:41
 */
@Data
@Builder
public class CreateUserRequest {
    private String nickName;
    private Integer integration;
    private Integer level;
    private Integer visit;
    private Integer active;
    private List<String> roles;
}
