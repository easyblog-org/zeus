package top.easyblog.titan.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;
import top.easyblog.titan.response.ZeusResultCode;

/**
 * @author: frank.huang
 * @date: 2021-11-01 17:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {

    private ZeusResultCode code;

    public BusinessException(@NonNull ZeusResultCode code) {
        this.code = code;
    }

    public BusinessException(@NonNull ZeusResultCode code, String message){
        super(message);
        this.code=code;
    }

    public BusinessException(@NonNull ZeusResultCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }


    public String getCode(){
        return this.code.getCode();
    }

}
