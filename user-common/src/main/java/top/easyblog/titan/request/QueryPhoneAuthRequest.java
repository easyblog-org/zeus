package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: frank.huang
 * @date: 2022-02-10 23:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryPhoneAuthRequest {
    private Long id;

    private Integer phoneAreaCode;

    private String phone;

    private Date createTime;

}
