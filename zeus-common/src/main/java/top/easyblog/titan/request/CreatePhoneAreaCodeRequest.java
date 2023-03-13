package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: frank.huang
 * @date: 2022-02-10 20:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhoneAreaCodeRequest {

    @NotBlank(message = "Required parameter `continent_code` is not present!")
    private String continentCode;

    /**
     * 国际冠码 eg:+86
     */
    @NotBlank(message = "Required parameter `crown_code` is not present!")
    private String crownCode;
    /**
     * 国家码
     */
    @NotBlank(message = "Required parameter `country_code` is not present!")
    private String countryCode;
    /**
     * 区域名称
     */
    @NotBlank(message = "Required parameter `area_name` is not present!")
    private String areaName;
}
