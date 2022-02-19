package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2022-02-10 23:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhoneAuthRequest {
    private String phoneAreaCode;

    private String phone;
}
