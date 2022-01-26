package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.jdbc.SQL;
import top.easyblog.titan.dao.auto.model.UserHeaderImg;

public class UserHeaderImgSqlProvider {

    public String insertSelective(UserHeaderImg record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user_header_img");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getHeaderImgUrl() != null) {
            sql.VALUES("header_img_url", "#{headerImgUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=BIGINT}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(UserHeaderImg record) {
        SQL sql = new SQL();
        sql.UPDATE("user_header_img");
        
        if (record.getHeaderImgUrl() != null) {
            sql.SET("header_img_url = #{headerImgUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            sql.SET("user_id = #{userId,jdbcType=BIGINT}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}