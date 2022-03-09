package top.easyblog.titan.dao.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author: frank.huang
 * @date: 2022-03-07 21:43
 */
@Mapper
public interface MySignInLogMapper {

    @Update({
            "update sign_in_log set status = #{status,jdbcType=INTEGER} where token = #{token,jdbcType=VARCHAR}"
    })
    int updateByTokenSelective(@Param("token") String token, @Param("status") int status);

}
