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
import top.easyblog.titan.dao.auto.model.UserHeaderImg;
import top.easyblog.titan.dao.auto.model.UserHeaderImgExample;

public interface UserHeaderImgMapper {
    @SelectProvider(type=UserHeaderImgSqlProvider.class, method="countByExample")
    long countByExample(UserHeaderImgExample example);

    @DeleteProvider(type=UserHeaderImgSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserHeaderImgExample example);

    @Delete({
        "delete from user_header_img",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_header_img (user_id, header_img_url, ",
        "status, create_time, ",
        "update_time)",
        "values (#{userId,jdbcType=BIGINT}, #{headerImgUrl,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(UserHeaderImg record);

    @InsertProvider(type=UserHeaderImgSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(UserHeaderImg record);

    @SelectProvider(type=UserHeaderImgSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="header_img_url", property="headerImgUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<UserHeaderImg> selectByExample(UserHeaderImgExample example);

    @Select({
        "select",
        "id, user_id, header_img_url, status, create_time, update_time",
        "from user_header_img",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="header_img_url", property="headerImgUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    UserHeaderImg selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserHeaderImgSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserHeaderImg record, @Param("example") UserHeaderImgExample example);

    @UpdateProvider(type=UserHeaderImgSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") UserHeaderImg record, @Param("example") UserHeaderImgExample example);

    @UpdateProvider(type=UserHeaderImgSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserHeaderImg record);

    @Update({
        "update user_header_img",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "header_img_url = #{headerImgUrl,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserHeaderImg record);
}