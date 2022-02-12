package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import top.easyblog.titan.dao.auto.model.User;
import top.easyblog.titan.dao.auto.model.UserExample;

import java.util.List;

@Mapper
public interface UserMapper {
    @SelectProvider(type = UserSqlProvider.class, method = "countByExample")
    long countByExample(UserExample example);

    @DeleteProvider(type = UserSqlProvider.class, method = "deleteByExample")
    int deleteByExample(UserExample example);

    @Insert({
            "insert into user (id, nick_name, ",
            "integration,",
            "level, visit, active, ",
            "create_time, update_time)",
            "values (#{id,jdbcType=BIGINT}, #{nickName,jdbcType=VARCHAR}, ",
            "#{integration,jdbcType=INTEGER},",
            "#{level,jdbcType=INTEGER}, #{visit,jdbcType=INTEGER}, #{active,jdbcType=INTEGER}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User record);

    @InsertProvider(type = UserSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertSelective(User record);

    @SelectProvider(type = UserSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "nick_name", property = "nickName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "integration", property = "integration", jdbcType = JdbcType.INTEGER),
            @Result(column = "level", property = "level", jdbcType = JdbcType.INTEGER),
            @Result(column = "visit", property = "visit", jdbcType = JdbcType.INTEGER),
            @Result(column = "active", property = "active", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<User> selectByExample(UserExample example);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    @Update({
            "update user",
            "set nick_name = #{nick_name,jdbcType=VARCHAR},",
            "integration = #{integration,jdbcType=INTEGER},",
            "level = #{level,jdbcType=INTEGER},",
            "visit = #{visit,jdbcType=INTEGER},",
            "active = #{active,jdbcType=INTEGER},",
            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(@Param("record") User record);
}