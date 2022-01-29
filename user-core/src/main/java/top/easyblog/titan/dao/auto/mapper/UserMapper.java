package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

import top.easyblog.titan.dao.auto.model.User;

@Mapper
public interface UserMapper {
    @Insert({
            "insert into user (id, nick_name, ",
            "integration, header_img_id, ",
            "level, visit, active, ",
            "create_time, update_time)",
            "values (#{id,jdbcType=BIGINT}, #{nickName,jdbcType=VARCHAR}, ",
            "#{integration,jdbcType=INTEGER}, #{headerImgId,jdbcType=INTEGER}, ",
            "#{level,jdbcType=INTEGER}, #{visit,jdbcType=INTEGER}, #{active,jdbcType=INTEGER}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(User record);

    @InsertProvider(type = UserSqlProvider.class, method = "insertSelective")
    int insertSelective(User record);
}