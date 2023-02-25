package top.easyblog.titan.dao.auto.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import top.easyblog.titan.dao.auto.model.UserRoles;
import top.easyblog.titan.dao.auto.model.UserRolesExample;

@Mapper
public interface UserRolesMapper {
    @SelectProvider(type=UserRolesSqlProvider.class, method="countByExample")
    long countByExample(UserRolesExample example);

    @DeleteProvider(type=UserRolesSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserRolesExample example);

    @Delete({
        "delete from user_roles",
        "where user_id = #{userId,jdbcType=BIGINT}",
          "and role_id = #{roleId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(UserRoles key);

    @Insert({
        "insert into user_roles (user_id, role_id, ",
        "enabled, create_time, ",
        "update_time)",
        "values (#{userId,jdbcType=BIGINT}, #{roleId,jdbcType=BIGINT}, ",
        "#{enabled,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(UserRoles record);

    @InsertProvider(type=UserRolesSqlProvider.class, method="insertSelective")
    int insertSelective(UserRoles record);

    @SelectProvider(type=UserRolesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="enabled", property="enabled", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<UserRoles> selectByExample(UserRolesExample example);

    @Select({
        "select",
        "user_id, role_id, enabled, create_time, update_time",
        "from user_roles",
        "where user_id = #{userId,jdbcType=BIGINT}",
          "and role_id = #{roleId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="enabled", property="enabled", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    UserRoles selectByPrimaryKey(UserRoles key);

    @UpdateProvider(type=UserRolesSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserRoles record, @Param("example") UserRolesExample example);

    @UpdateProvider(type=UserRolesSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") UserRoles record, @Param("example") UserRolesExample example);

    @UpdateProvider(type=UserRolesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserRoles record);

    @Update({
        "update user_roles",
        "set enabled = #{enabled,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where user_id = #{userId,jdbcType=BIGINT}",
          "and role_id = #{roleId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserRoles record);
}