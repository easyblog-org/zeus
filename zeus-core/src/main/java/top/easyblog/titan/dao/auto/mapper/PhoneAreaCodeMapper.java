package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import top.easyblog.titan.dao.auto.model.PhoneAreaCode;
import top.easyblog.titan.dao.auto.model.PhoneAreaCodeExample;

import java.util.List;

@Mapper
public interface PhoneAreaCodeMapper {
    @SelectProvider(type = PhoneAreaCodeSqlProvider.class, method = "countByExample")
    long countByExample(PhoneAreaCodeExample example);

    @DeleteProvider(type = PhoneAreaCodeSqlProvider.class, method = "deleteByExample")
    int deleteByExample(PhoneAreaCodeExample example);

    @Delete({
            "delete from phone_area_code",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into phone_area_code (continent_code, crown_code, ",
            "country_code, area_code, ",
            "area_name, create_time, ",
            "update_time)",
            "values (#{continentCode,jdbcType=VARCHAR}, #{crownCode,jdbcType=VARCHAR}, ",
            "#{countryCode,jdbcType=VARCHAR}, #{areaCode,jdbcType=VARCHAR}, ",
            "#{areaName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insert(PhoneAreaCode record);

    @InsertProvider(type = PhoneAreaCodeSqlProvider.class, method = "insertSelective")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insertSelective(PhoneAreaCode record);

    @SelectProvider(type = PhoneAreaCodeSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "continent_code", property = "continentCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "crown_code", property = "crownCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "country_code", property = "countryCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "area_code", property = "areaCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "area_name", property = "areaName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<PhoneAreaCode> selectByExample(PhoneAreaCodeExample example);

    @Select({
            "select",
            "id, continent_code, crown_code, country_code, area_code, area_name, create_time, ",
            "update_time",
            "from phone_area_code",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "continent_code", property = "continentCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "crown_code", property = "crownCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "country_code", property = "countryCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "area_code", property = "areaCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "area_name", property = "areaName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    PhoneAreaCode selectByPrimaryKey(Long id);

    @UpdateProvider(type = PhoneAreaCodeSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") PhoneAreaCode record, @Param("example") PhoneAreaCodeExample example);

    @UpdateProvider(type = PhoneAreaCodeSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") PhoneAreaCode record, @Param("example") PhoneAreaCodeExample example);

    @UpdateProvider(type = PhoneAreaCodeSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PhoneAreaCode record);

    @Update({
            "update phone_area_code",
            "set continent_code = #{continentCode,jdbcType=VARCHAR},",
            "crown_code = #{crownCode,jdbcType=VARCHAR},",
            "country_code = #{countryCode,jdbcType=VARCHAR},",
            "area_code = #{areaCode,jdbcType=VARCHAR},",
            "area_name = #{areaName,jdbcType=VARCHAR},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PhoneAreaCode record);
}