package top.easyblog.titan.dao.auto.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import top.easyblog.titan.dao.auto.model.Roles;
import top.easyblog.titan.dao.auto.model.RolesExample.Criteria;
import top.easyblog.titan.dao.auto.model.RolesExample.Criterion;
import top.easyblog.titan.dao.auto.model.RolesExample;

public class RolesSqlProvider {

    public String countByExample(RolesExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("roles");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(RolesExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("roles");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Roles record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("roles");
        
        if (record.getCode() != null) {
            sql.VALUES("code", "#{code,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=SMALLINT}");
        }
        
        if (record.getDesc() != null) {
            sql.VALUES("desc", "#{desc,jdbcType=VARCHAR}");
        }
        
        if (record.getEnabled() != null) {
            sql.VALUES("enabled", "#{enabled,jdbcType=BIT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(RolesExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("code");
        sql.SELECT("name");
        sql.SELECT("desc");
        sql.SELECT("enabled");
        sql.SELECT("create_time");
        sql.SELECT("update_time");
        sql.FROM("roles");
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
        Roles record = (Roles) parameter.get("record");
        RolesExample example = (RolesExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("roles");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getCode() != null) {
            sql.SET("code = #{record.code,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("name = #{record.name,jdbcType=SMALLINT}");
        }
        
        if (record.getDesc() != null) {
            sql.SET("desc = #{record.desc,jdbcType=VARCHAR}");
        }
        
        if (record.getEnabled() != null) {
            sql.SET("enabled = #{record.enabled,jdbcType=BIT}");
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
        sql.UPDATE("roles");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("code = #{record.code,jdbcType=VARCHAR}");
        sql.SET("name = #{record.name,jdbcType=SMALLINT}");
        sql.SET("desc = #{record.desc,jdbcType=VARCHAR}");
        sql.SET("enabled = #{record.enabled,jdbcType=BIT}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        
        RolesExample example = (RolesExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Roles record) {
        SQL sql = new SQL();
        sql.UPDATE("roles");
        
        if (record.getCode() != null) {
            sql.SET("code = #{code,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=SMALLINT}");
        }
        
        if (record.getDesc() != null) {
            sql.SET("desc = #{desc,jdbcType=VARCHAR}");
        }
        
        if (record.getEnabled() != null) {
            sql.SET("enabled = #{enabled,jdbcType=BIT}");
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

    protected void applyWhere(SQL sql, RolesExample example, boolean includeExamplePhrase) {
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
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
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