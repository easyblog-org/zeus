package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import top.easyblog.titan.dao.auto.model.Account;


public interface AccountMapper {
    @Delete({
            "delete from account",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into account (id, user_id, ",
            "identity_type, identifier, ",
            "credential, verified, ",
            "status, create_time, ",
            "update_time)",
            "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
            "#{identityType,jdbcType=INTEGER}, #{identifier,jdbcType=VARCHAR}, ",
            "#{credential,jdbcType=VARCHAR}, #{verified,jdbcType=INTEGER}, ",
            "#{status,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Account record);

    @InsertProvider(type = AccountSqlProvider.class, method = "insertSelective")
    int insertSelective(Account record);

    @Select({
            "select",
            "id, user_id, identity_type, identifier, credential, verified, status, create_time, ",
            "update_time",
            "from account",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "identity_type", property = "identityType", jdbcType = JdbcType.INTEGER),
            @Result(column = "identifier", property = "identifier", jdbcType = JdbcType.VARCHAR),
            @Result(column = "credential", property = "credential", jdbcType = JdbcType.VARCHAR),
            @Result(column = "verified", property = "verified", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    Account selectByPrimaryKey(Long id);

    @UpdateProvider(type = AccountSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Account record);

    @Update({
            "update account",
            "set user_id = #{userId,jdbcType=BIGINT},",
            "identity_type = #{identityType,jdbcType=INTEGER},",
            "identifier = #{identifier,jdbcType=VARCHAR},",
            "credential = #{credential,jdbcType=VARCHAR},",
            "verified = #{verified,jdbcType=INTEGER},",
            "status = #{status,jdbcType=INTEGER},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Account record);
}