package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.jdbc.SQL;
import top.easyblog.titan.dao.auto.model.PhoneAreaCode;

public class PhoneAreaCodeSqlProvider {

    public String insertSelective(PhoneAreaCode record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("phone_area_code");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getCrownCode() != null) {
            sql.VALUES("crown_code", "#{crownCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountryCode() != null) {
            sql.VALUES("country_code", "#{countryCode,jdbcType=VARCHAR}");
        }
        
        if (record.getAreaCode() != null) {
            sql.VALUES("area_code", "#{areaCode,jdbcType=VARCHAR}");
        }
        
        if (record.getAreaName() != null) {
            sql.VALUES("area_name", "#{areaName,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(PhoneAreaCode record) {
        SQL sql = new SQL();
        sql.UPDATE("phone_area_code");
        
        if (record.getCrownCode() != null) {
            sql.SET("crown_code = #{crownCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCountryCode() != null) {
            sql.SET("country_code = #{countryCode,jdbcType=VARCHAR}");
        }
        
        if (record.getAreaCode() != null) {
            sql.SET("area_code = #{areaCode,jdbcType=VARCHAR}");
        }
        
        if (record.getAreaName() != null) {
            sql.SET("area_name = #{areaName,jdbcType=VARCHAR}");
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