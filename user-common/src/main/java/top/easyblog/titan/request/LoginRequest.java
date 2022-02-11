package top.easyblog.titan.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.titan.enums.IdentifierType;

/**
 * @author: frank.huang
 * @date: 2021-11-01 20:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements BaseRequest {
    @NotBlank(message = "Required parameter `identifierType` is not present")
    private Byte identifierType;
    @NotBlank(message = "Required parameter `identifier` is not present")
    private String identifier;
    @NotBlank(message = "Required parameter `credential` is not present")
    private String credential;


    public boolean validate() {
        return IdentifierType.codeOf(identifierType) != null;
    }
}
