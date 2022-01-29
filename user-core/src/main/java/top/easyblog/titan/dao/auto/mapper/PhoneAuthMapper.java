package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import top.easyblog.titan.dao.auto.model.PhoneAuth;
import top.easyblog.titan.dao.auto.model.PhoneAuthExample;

import java.util.List;

@Mapper
public interface PhoneAuthMapper {
    @SelectProvider(type = PhoneAuthSqlProvider.class, method = "countByExample")
    long countByExample(PhoneAuthExample example);

    @DeleteProvider(type = PhoneAuthSqlProvider.class, method = "deleteByExample")
    int deleteByExample(PhoneAuthExample example);

    @Delete({
            "delete from phone_auth",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into phone_auth (id, phone_area_code, ",
            "phone, create_time, ",
            "update_time)",
            "values (#{id,jdbcType=BIGINT}, #{phoneAreaCode,jdbcType=INTEGER}, ",
            "#{phone,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(PhoneAuth record);

    @InsertProvider(type = PhoneAuthSqlProvider.class, method = "insertSelective")
    int insertSelective(PhoneAuth record);

    @SelectProvider(type = PhoneAuthSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "phone_area_code", property = "phoneAreaCode", jdbcType = JdbcType.INTEGER),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<PhoneAuth> selectByExample(PhoneAuthExample example);

    @Select({
            "select",
            "id, phone_area_code, phone, create_time, update_time",
            "from phone_auth",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "phone_area_code", property = "phoneAreaCode", jdbcType = JdbcType.INTEGER),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    PhoneAuth selectByPrimaryKey(Long id);

    @UpdateProvider(type = PhoneAuthSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PhoneAuth record, @Param("example") PhoneAuthExample example);

    @UpdateProvider(type = PhoneAuthSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") PhoneAuth record, @Param("example") PhoneAuthExample example);

    @UpdateProvider(type = PhoneAuthSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PhoneAuth record);

    @Update({
            "update phone_auth",
            "set phone_area_code = #{phoneAreaCode,jdbcType=INTEGER},",
            "phone = #{phone,jdbcType=VARCHAR},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PhoneAuth record);
}