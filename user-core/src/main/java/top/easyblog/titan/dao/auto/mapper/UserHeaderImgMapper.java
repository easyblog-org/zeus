package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import top.easyblog.titan.dao.auto.model.UserHeaderImg;

@Mapper
public interface UserHeaderImgMapper {
    @Delete({
            "delete from user_header_img",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into user_header_img (id, header_img_url, ",
            "user_id, status, create_time, ",
            "update_time)",
            "values (#{id,jdbcType=BIGINT}, #{headerImgUrl,jdbcType=VARCHAR}, ",
            "#{userId,jdbcType=BIGINT}, #{status,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(UserHeaderImg record);

    @InsertProvider(type = UserHeaderImgSqlProvider.class, method = "insertSelective")
    int insertSelective(UserHeaderImg record);

    @Select({
            "select",
            "id, header_img_url, user_id, status, create_time, update_time",
            "from user_header_img",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "header_img_url", property = "headerImgUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    UserHeaderImg selectByPrimaryKey(Long id);

    @UpdateProvider(type = UserHeaderImgSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserHeaderImg record);

    @Update({
            "update user_header_img",
            "set header_img_url = #{headerImgUrl,jdbcType=VARCHAR},",
            "user_id = #{userId,jdbcType=BIGINT},",
            "status = #{status,jdbcType=INTEGER},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserHeaderImg record);
}