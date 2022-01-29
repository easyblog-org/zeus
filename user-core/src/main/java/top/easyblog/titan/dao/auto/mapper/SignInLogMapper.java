package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import top.easyblog.titan.dao.auto.model.SignInLog;
import top.easyblog.titan.dao.auto.model.SignInLogExample;

import java.util.List;

@Mapper
public interface SignInLogMapper {
    @SelectProvider(type = SignInLogSqlProvider.class, method = "countByExample")
    long countByExample(SignInLogExample example);

    @DeleteProvider(type = SignInLogSqlProvider.class, method = "deleteByExample")
    int deleteByExample(SignInLogExample example);

    @Delete({
            "delete from sign_in_log",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sign_in_log (id, user_id, ",
            "status, ip, device, ",
            "operation_system, location, ",
            "create_time, update_time)",
            "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
            "#{status,jdbcType=INTEGER}, #{ip,jdbcType=VARCHAR}, #{device,jdbcType=VARCHAR}, ",
            "#{operationSystem,jdbcType=VARCHAR}, #{location,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(SignInLog record);

    @InsertProvider(type = SignInLogSqlProvider.class, method = "insertSelective")
    int insertSelective(SignInLog record);

    @SelectProvider(type = SignInLogSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "ip", property = "ip", jdbcType = JdbcType.VARCHAR),
            @Result(column = "device", property = "device", jdbcType = JdbcType.VARCHAR),
            @Result(column = "operation_system", property = "operationSystem", jdbcType = JdbcType.VARCHAR),
            @Result(column = "location", property = "location", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<SignInLog> selectByExample(SignInLogExample example);

    @Select({
            "select",
            "id, user_id, status, ip, device, operation_system, location, create_time, update_time",
            "from sign_in_log",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "ip", property = "ip", jdbcType = JdbcType.VARCHAR),
            @Result(column = "device", property = "device", jdbcType = JdbcType.VARCHAR),
            @Result(column = "operation_system", property = "operationSystem", jdbcType = JdbcType.VARCHAR),
            @Result(column = "location", property = "location", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    SignInLog selectByPrimaryKey(Long id);

    @UpdateProvider(type = SignInLogSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") SignInLog record, @Param("example") SignInLogExample example);

    @UpdateProvider(type = SignInLogSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") SignInLog record, @Param("example") SignInLogExample example);

    @UpdateProvider(type = SignInLogSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SignInLog record);

    @Update({
            "update sign_in_log",
            "set user_id = #{userId,jdbcType=BIGINT},",
            "status = #{status,jdbcType=INTEGER},",
            "ip = #{ip,jdbcType=VARCHAR},",
            "device = #{device,jdbcType=VARCHAR},",
            "operation_system = #{operationSystem,jdbcType=VARCHAR},",
            "location = #{location,jdbcType=VARCHAR},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SignInLog record);
}