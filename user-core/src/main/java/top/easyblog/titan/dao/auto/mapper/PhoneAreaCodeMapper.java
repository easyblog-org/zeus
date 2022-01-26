package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import top.easyblog.titan.dao.auto.model.PhoneAreaCode;


public interface PhoneAreaCodeMapper {
    @Delete({
            "delete from phone_area_code",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into phone_area_code (id, crown_code, ",
            "country_code, area_code, ",
            "area_name, create_time, ",
            "update_time)",
            "values (#{id,jdbcType=BIGINT}, #{crownCode,jdbcType=VARCHAR}, ",
            "#{countryCode,jdbcType=VARCHAR}, #{areaCode,jdbcType=VARCHAR}, ",
            "#{areaName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(PhoneAreaCode record);

    @InsertProvider(type = PhoneAreaCodeSqlProvider.class, method = "insertSelective")
    int insertSelective(PhoneAreaCode record);

    @Select({
            "select",
            "id, crown_code, country_code, area_code, area_name, create_time, update_time",
            "from phone_area_code",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "crown_code", property = "crownCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "country_code", property = "countryCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "area_code", property = "areaCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "area_name", property = "areaName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    PhoneAreaCode selectByPrimaryKey(Long id);

    @UpdateProvider(type = PhoneAreaCodeSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PhoneAreaCode record);

    @Update({
            "update phone_area_code",
            "set crown_code = #{crownCode,jdbcType=VARCHAR},",
            "country_code = #{countryCode,jdbcType=VARCHAR},",
            "area_code = #{areaCode,jdbcType=VARCHAR},",
            "area_name = #{areaName,jdbcType=VARCHAR},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PhoneAreaCode record);
}