package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2022-02-10 21:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryPhoneAreaCodeRequest {
    private Long id;

    private String continentCode;
    ;

    private String crownCode;

    private String countryCode;

    private String areaName;
}
