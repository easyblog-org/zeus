package top.easyblog.titan.request;

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
    private Byte identifierType;
    private String identifier;
    private String credential;


    public boolean validate() {
        return !IdentifierType.codeOf(identifierType).equals(IdentifierType.UNKNOWN);
    }
}
