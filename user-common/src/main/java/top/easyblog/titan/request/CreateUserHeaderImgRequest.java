package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/02/06 11:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserHeaderImgRequest {
      private Long userId;
      private String headerImgUrl;
      private Integer status;
}
