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
    /**
     * 国际冠码 eg:+86
     */
    @NotBlank(message = "Required parameter `crownCode` is not present!")
    private String crownCode;
    /**
     * 国家码
     */
    @NotBlank(message = "Required parameter `countryCode` is not present!")
    private String countryCode;
    /**
     * 区域码
     */
    @NotBlank(message = "Required parameter `areaCode` is not present!")
    private String areaCode;
    /**
     * 区域名称
     */
    @NotBlank(message = "Required parameter `areaName` is not present!")
    private String areaName;
}
