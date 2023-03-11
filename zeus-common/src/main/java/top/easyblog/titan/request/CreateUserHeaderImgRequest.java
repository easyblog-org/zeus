package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author frank.huang
 * @date 2022/02/06 11:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserHeaderImgRequest {
    @NotNull(message = "Required param 'user_id' is not present.")
    private Long userId;
    private String headerImgUrl;
}
