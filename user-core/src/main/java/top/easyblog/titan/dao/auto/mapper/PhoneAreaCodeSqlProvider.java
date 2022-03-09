package top.easyblog.titan.dao.auto.mapper;

import org.apache.ibatis.jdbc.SQL;
import top.easyblog.titan.dao.auto.model.PhoneAreaCode;
import top.easyblog.titan.dao.auto.model.PhoneAreaCodeExample;
import top.easyblog.titan.dao.auto.model.PhoneAreaCodeExample.Criteria;
import top.easyblog.titan.dao.auto.model.PhoneAreaCodeExample.Criterion;

import java.util.List;
import java.util.Map;

public class PhoneAreaCodeSqlProvider {

    public String countByExample(PhoneAreaCodeExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("phone_area_code");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(PhoneAreaCodeExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("phone_area_code");
        applyWhere(sql, example, false);
        return sql.toString();
    }

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

    public String selectByExample(PhoneAreaCodeExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("crown_code");
        sql.SELECT("country_code");
        sql.SELECT("area_code");
        sql.SELECT("area_name");
        sql.SELECT("create_time");
        sql.SELECT("update_time");
        sql.FROM("phone_area_code");
        applyWhere(sql, example, false);

        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }

        StringBuilder sqlBuilder = new StringBuilder(sql.toString());
        if (example != null && example.getOffset() != null && example.getLimit() >= 0) {
            sqlBuilder.append(" LIMIT ").append(example.getOffset());
            if (example.getLimit() != null && example.getLimit() > 0) {
                sqlBuilder.append(",").append(example.getLimit());
            }
        }
        return sqlBuilder.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        PhoneAreaCode record = (PhoneAreaCode) parameter.get("record");
        PhoneAreaCodeExample example = (PhoneAreaCodeExample) parameter.get("example");

        SQL sql = new SQL();
        sql.UPDATE("phone_area_code");

        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }

        if (record.getCrownCode() != null) {
            sql.SET("crown_code = #{record.crownCode,jdbcType=VARCHAR}");
        }

        if (record.getCountryCode() != null) {
            sql.SET("country_code = #{record.countryCode,jdbcType=VARCHAR}");
        }

        if (record.getAreaCode() != null) {
            sql.SET("area_code = #{record.areaCode,jdbcType=VARCHAR}");
        }

        if (record.getAreaName() != null) {
            sql.SET("area_name = #{record.areaName,jdbcType=VARCHAR}");
        }

        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }

        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("phone_area_code");

        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("crown_code = #{record.crownCode,jdbcType=VARCHAR}");
        sql.SET("country_code = #{record.countryCode,jdbcType=VARCHAR}");
        sql.SET("area_code = #{record.areaCode,jdbcType=VARCHAR}");
        sql.SET("area_name = #{record.areaName,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");

        PhoneAreaCodeExample example = (PhoneAreaCodeExample) parameter.get("example");
        applyWhere(sql, example, true);
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

    protected void applyWhere(SQL sql, PhoneAreaCodeExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }

        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }

        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }

                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }

                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }

        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}