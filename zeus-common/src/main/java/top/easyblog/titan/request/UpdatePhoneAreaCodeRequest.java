package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2022-02-10 22:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePhoneAreaCodeRequest {
    private String crownCode;

    private String countryCode;

    private String areaCode;

    private String areaName;
}
