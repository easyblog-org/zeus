package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.jdbc.SQL;
import top.easyblog.titan.dao.auto.model.Account;

public class AccountSqlProvider {

    public String insertSelective(Account record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("account");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=BIGINT}");
        }
        
        if (record.getIdentityType() != null) {
            sql.VALUES("identity_type", "#{identityType,jdbcType=INTEGER}");
        }
        
        if (record.getIdentifier() != null) {
            sql.VALUES("identifier", "#{identifier,jdbcType=VARCHAR}");
        }
        
        if (record.getCredential() != null) {
            sql.VALUES("credential", "#{credential,jdbcType=VARCHAR}");
        }
        
        if (record.getVerified() != null) {
            sql.VALUES("verified", "#{verified,jdbcType=INTEGER}");
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

    public String updateByPrimaryKeySelective(Account record) {
        SQL sql = new SQL();
        sql.UPDATE("account");
        
        if (record.getUserId() != null) {
            sql.SET("user_id = #{userId,jdbcType=BIGINT}");
        }
        
        if (record.getIdentityType() != null) {
            sql.SET("identity_type = #{identityType,jdbcType=INTEGER}");
        }
        
        if (record.getIdentifier() != null) {
            sql.SET("identifier = #{identifier,jdbcType=VARCHAR}");
        }
        
        if (record.getCredential() != null) {
            sql.SET("credential = #{credential,jdbcType=VARCHAR}");
        }
        
        if (record.getVerified() != null) {
            sql.SET("verified = #{verified,jdbcType=INTEGER}");
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