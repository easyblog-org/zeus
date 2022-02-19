package top.easyblog.titan.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2022-01-29 22:13
 */
@Data
@Builder
public class CreateAccountRequest {
    private Long id;

    private Long userId;

    private Integer identityType;

    private String identifier;

    private String credential;

    private Integer verified;

    private Integer status;

    private Boolean createDirect;
}
