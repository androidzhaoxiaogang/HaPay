package com.hengaiw.model.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CloudConfigExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    private Integer limit;

    private Integer offset;

    public CloudConfigExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria implements Serializable {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCloud_config_idIsNull() {
            addCriterion("cloud_config_id is null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idIsNotNull() {
            addCriterion("cloud_config_id is not null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idEqualTo(Integer value) {
            addCriterion("cloud_config_id =", value, "cloud_config_id");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idNotEqualTo(Integer value) {
            addCriterion("cloud_config_id <>", value, "cloud_config_id");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idGreaterThan(Integer value) {
            addCriterion("cloud_config_id >", value, "cloud_config_id");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("cloud_config_id >=", value, "cloud_config_id");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idLessThan(Integer value) {
            addCriterion("cloud_config_id <", value, "cloud_config_id");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idLessThanOrEqualTo(Integer value) {
            addCriterion("cloud_config_id <=", value, "cloud_config_id");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idIn(List<Integer> values) {
            addCriterion("cloud_config_id in", values, "cloud_config_id");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idNotIn(List<Integer> values) {
            addCriterion("cloud_config_id not in", values, "cloud_config_id");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idBetween(Integer value1, Integer value2) {
            addCriterion("cloud_config_id between", value1, value2, "cloud_config_id");
            return (Criteria) this;
        }

        public Criteria andCloud_config_idNotBetween(Integer value1, Integer value2) {
            addCriterion("cloud_config_id not between", value1, value2, "cloud_config_id");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameIsNull() {
            addCriterion("cloud_config_name is null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameIsNotNull() {
            addCriterion("cloud_config_name is not null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameEqualTo(String value) {
            addCriterion("cloud_config_name =", value, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameNotEqualTo(String value) {
            addCriterion("cloud_config_name <>", value, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameGreaterThan(String value) {
            addCriterion("cloud_config_name >", value, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_config_name >=", value, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameLessThan(String value) {
            addCriterion("cloud_config_name <", value, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameLessThanOrEqualTo(String value) {
            addCriterion("cloud_config_name <=", value, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameLike(String value) {
            addCriterion("cloud_config_name like", value, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameNotLike(String value) {
            addCriterion("cloud_config_name not like", value, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameIn(List<String> values) {
            addCriterion("cloud_config_name in", values, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameNotIn(List<String> values) {
            addCriterion("cloud_config_name not in", values, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameBetween(String value1, String value2) {
            addCriterion("cloud_config_name between", value1, value2, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_nameNotBetween(String value1, String value2) {
            addCriterion("cloud_config_name not between", value1, value2, "cloud_config_name");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyIsNull() {
            addCriterion("cloud_config_key is null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyIsNotNull() {
            addCriterion("cloud_config_key is not null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyEqualTo(String value) {
            addCriterion("cloud_config_key =", value, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyNotEqualTo(String value) {
            addCriterion("cloud_config_key <>", value, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyGreaterThan(String value) {
            addCriterion("cloud_config_key >", value, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_config_key >=", value, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyLessThan(String value) {
            addCriterion("cloud_config_key <", value, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyLessThanOrEqualTo(String value) {
            addCriterion("cloud_config_key <=", value, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyLike(String value) {
            addCriterion("cloud_config_key like", value, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyNotLike(String value) {
            addCriterion("cloud_config_key not like", value, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyIn(List<String> values) {
            addCriterion("cloud_config_key in", values, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyNotIn(List<String> values) {
            addCriterion("cloud_config_key not in", values, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyBetween(String value1, String value2) {
            addCriterion("cloud_config_key between", value1, value2, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_keyNotBetween(String value1, String value2) {
            addCriterion("cloud_config_key not between", value1, value2, "cloud_config_key");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descIsNull() {
            addCriterion("cloud_config_desc is null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descIsNotNull() {
            addCriterion("cloud_config_desc is not null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descEqualTo(String value) {
            addCriterion("cloud_config_desc =", value, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descNotEqualTo(String value) {
            addCriterion("cloud_config_desc <>", value, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descGreaterThan(String value) {
            addCriterion("cloud_config_desc >", value, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_config_desc >=", value, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descLessThan(String value) {
            addCriterion("cloud_config_desc <", value, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descLessThanOrEqualTo(String value) {
            addCriterion("cloud_config_desc <=", value, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descLike(String value) {
            addCriterion("cloud_config_desc like", value, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descNotLike(String value) {
            addCriterion("cloud_config_desc not like", value, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descIn(List<String> values) {
            addCriterion("cloud_config_desc in", values, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descNotIn(List<String> values) {
            addCriterion("cloud_config_desc not in", values, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descBetween(String value1, String value2) {
            addCriterion("cloud_config_desc between", value1, value2, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_descNotBetween(String value1, String value2) {
            addCriterion("cloud_config_desc not between", value1, value2, "cloud_config_desc");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueIsNull() {
            addCriterion("cloud_config_dev_value is null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueIsNotNull() {
            addCriterion("cloud_config_dev_value is not null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueEqualTo(String value) {
            addCriterion("cloud_config_dev_value =", value, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueNotEqualTo(String value) {
            addCriterion("cloud_config_dev_value <>", value, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueGreaterThan(String value) {
            addCriterion("cloud_config_dev_value >", value, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_config_dev_value >=", value, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueLessThan(String value) {
            addCriterion("cloud_config_dev_value <", value, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueLessThanOrEqualTo(String value) {
            addCriterion("cloud_config_dev_value <=", value, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueLike(String value) {
            addCriterion("cloud_config_dev_value like", value, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueNotLike(String value) {
            addCriterion("cloud_config_dev_value not like", value, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueIn(List<String> values) {
            addCriterion("cloud_config_dev_value in", values, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueNotIn(List<String> values) {
            addCriterion("cloud_config_dev_value not in", values, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueBetween(String value1, String value2) {
            addCriterion("cloud_config_dev_value between", value1, value2, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_dev_valueNotBetween(String value1, String value2) {
            addCriterion("cloud_config_dev_value not between", value1, value2, "cloud_config_dev_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueIsNull() {
            addCriterion("cloud_config_test_value is null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueIsNotNull() {
            addCriterion("cloud_config_test_value is not null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueEqualTo(String value) {
            addCriterion("cloud_config_test_value =", value, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueNotEqualTo(String value) {
            addCriterion("cloud_config_test_value <>", value, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueGreaterThan(String value) {
            addCriterion("cloud_config_test_value >", value, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_config_test_value >=", value, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueLessThan(String value) {
            addCriterion("cloud_config_test_value <", value, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueLessThanOrEqualTo(String value) {
            addCriterion("cloud_config_test_value <=", value, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueLike(String value) {
            addCriterion("cloud_config_test_value like", value, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueNotLike(String value) {
            addCriterion("cloud_config_test_value not like", value, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueIn(List<String> values) {
            addCriterion("cloud_config_test_value in", values, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueNotIn(List<String> values) {
            addCriterion("cloud_config_test_value not in", values, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueBetween(String value1, String value2) {
            addCriterion("cloud_config_test_value between", value1, value2, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_test_valueNotBetween(String value1, String value2) {
            addCriterion("cloud_config_test_value not between", value1, value2, "cloud_config_test_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueIsNull() {
            addCriterion("cloud_config_prod_value is null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueIsNotNull() {
            addCriterion("cloud_config_prod_value is not null");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueEqualTo(String value) {
            addCriterion("cloud_config_prod_value =", value, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueNotEqualTo(String value) {
            addCriterion("cloud_config_prod_value <>", value, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueGreaterThan(String value) {
            addCriterion("cloud_config_prod_value >", value, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_config_prod_value >=", value, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueLessThan(String value) {
            addCriterion("cloud_config_prod_value <", value, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueLessThanOrEqualTo(String value) {
            addCriterion("cloud_config_prod_value <=", value, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueLike(String value) {
            addCriterion("cloud_config_prod_value like", value, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueNotLike(String value) {
            addCriterion("cloud_config_prod_value not like", value, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueIn(List<String> values) {
            addCriterion("cloud_config_prod_value in", values, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueNotIn(List<String> values) {
            addCriterion("cloud_config_prod_value not in", values, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueBetween(String value1, String value2) {
            addCriterion("cloud_config_prod_value between", value1, value2, "cloud_config_prod_value");
            return (Criteria) this;
        }

        public Criteria andCloud_config_prod_valueNotBetween(String value1, String value2) {
            addCriterion("cloud_config_prod_value not between", value1, value2, "cloud_config_prod_value");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}