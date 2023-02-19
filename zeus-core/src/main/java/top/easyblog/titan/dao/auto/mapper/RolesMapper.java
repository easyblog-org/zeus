package top.easyblog.titan.dao.auto.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import top.easyblog.titan.dao.auto.model.Roles;
import top.easyblog.titan.dao.auto.model.RolesExample;

public interface RolesMapper {
    @SelectProvider(type=RolesSqlProvider.class, method="countByExample")
    long countByExample(RolesExample example);

    @DeleteProvider(type=RolesSqlProvider.class, method="deleteByExample")
    int deleteByExample(RolesExample example);

    @Delete({
        "delete from roles",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into roles (code, name, ",
        "desc, enabled, create_time, ",
        "update_time)",
        "values (#{code,jdbcType=VARCHAR}, #{name,jdbcType=SMALLINT}, ",
        "#{desc,jdbcType=VARCHAR}, #{enabled,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Roles record);

    @InsertProvider(type=RolesSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Roles record);

    @SelectProvider(type=RolesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.SMALLINT),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="enabled", property="enabled", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Roles> selectByExample(RolesExample example);

    @Select({
        "select",
        "id, code, name, desc, enabled, create_time, update_time",
        "from roles",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.SMALLINT),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="enabled", property="enabled", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Roles selectByPrimaryKey(Long id);

    @UpdateProvider(type=RolesSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Roles record, @Param("example") RolesExample example);

    @UpdateProvider(type=RolesSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Roles record, @Param("example") RolesExample example);

    @UpdateProvider(type=RolesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Roles record);

    @Update({
        "update roles",
        "set code = #{code,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=SMALLINT},",
          "desc = #{desc,jdbcType=VARCHAR},",
          "enabled = #{enabled,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Roles record);
}