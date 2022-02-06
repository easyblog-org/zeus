package top.easyblog.titan.request;

import lombok.Data;

/**
 * @author frank.huang
 * @date 2022/02/06 09:54
 */
@Data
public class UpdateAccountRequest {
    private Long id;

    private Long userId;

    private Integer identityType;

    private String identifier;

    private String credential;

    private Integer status;

    private Integer verified;
}
