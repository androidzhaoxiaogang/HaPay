package com.hengaiw.model.dao.model;

import java.io.Serializable;

public class CloudConfig implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private Integer cloud_config_id;

    /**
     * 应用名，GLOBAL为全局
     *
     * @mbg.generated
     */
    private String cloud_config_name;

    /**
     * 属性名
     *
     * @mbg.generated
     */
    private String cloud_config_key;

    /**
     * 参数描述
     *
     * @mbg.generated
     */
    private String cloud_config_desc;

    /**
     * 开发环境值
     *
     * @mbg.generated
     */
    private String cloud_config_dev_value;

    /**
     * 测试环境值
     *
     * @mbg.generated
     */
    private String cloud_config_test_value;

    /**
     * 正式环境值
     *
     * @mbg.generated
     */
    private String cloud_config_prod_value;

    private static final long serialVersionUID = 1L;

    public Integer getCloud_config_id() {
        return cloud_config_id;
    }

    public void setCloud_config_id(Integer cloud_config_id) {
        this.cloud_config_id = cloud_config_id;
    }

    public String getCloud_config_name() {
        return cloud_config_name;
    }

    public void setCloud_config_name(String cloud_config_name) {
        this.cloud_config_name = cloud_config_name;
    }

    public String getCloud_config_key() {
        return cloud_config_key;
    }

    public void setCloud_config_key(String cloud_config_key) {
        this.cloud_config_key = cloud_config_key;
    }

    public String getCloud_config_desc() {
        return cloud_config_desc;
    }

    public void setCloud_config_desc(String cloud_config_desc) {
        this.cloud_config_desc = cloud_config_desc;
    }

    public String getCloud_config_dev_value() {
        return cloud_config_dev_value;
    }

    public void setCloud_config_dev_value(String cloud_config_dev_value) {
        this.cloud_config_dev_value = cloud_config_dev_value;
    }

    public String getCloud_config_test_value() {
        return cloud_config_test_value;
    }

    public void setCloud_config_test_value(String cloud_config_test_value) {
        this.cloud_config_test_value = cloud_config_test_value;
    }

    public String getCloud_config_prod_value() {
        return cloud_config_prod_value;
    }

    public void setCloud_config_prod_value(String cloud_config_prod_value) {
        this.cloud_config_prod_value = cloud_config_prod_value;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CloudConfig other = (CloudConfig) that;
        return (this.getCloud_config_id() == null ? other.getCloud_config_id() == null : this.getCloud_config_id().equals(other.getCloud_config_id()))
            && (this.getCloud_config_name() == null ? other.getCloud_config_name() == null : this.getCloud_config_name().equals(other.getCloud_config_name()))
            && (this.getCloud_config_key() == null ? other.getCloud_config_key() == null : this.getCloud_config_key().equals(other.getCloud_config_key()))
            && (this.getCloud_config_desc() == null ? other.getCloud_config_desc() == null : this.getCloud_config_desc().equals(other.getCloud_config_desc()))
            && (this.getCloud_config_dev_value() == null ? other.getCloud_config_dev_value() == null : this.getCloud_config_dev_value().equals(other.getCloud_config_dev_value()))
            && (this.getCloud_config_test_value() == null ? other.getCloud_config_test_value() == null : this.getCloud_config_test_value().equals(other.getCloud_config_test_value()))
            && (this.getCloud_config_prod_value() == null ? other.getCloud_config_prod_value() == null : this.getCloud_config_prod_value().equals(other.getCloud_config_prod_value()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCloud_config_id() == null) ? 0 : getCloud_config_id().hashCode());
        result = prime * result + ((getCloud_config_name() == null) ? 0 : getCloud_config_name().hashCode());
        result = prime * result + ((getCloud_config_key() == null) ? 0 : getCloud_config_key().hashCode());
        result = prime * result + ((getCloud_config_desc() == null) ? 0 : getCloud_config_desc().hashCode());
        result = prime * result + ((getCloud_config_dev_value() == null) ? 0 : getCloud_config_dev_value().hashCode());
        result = prime * result + ((getCloud_config_test_value() == null) ? 0 : getCloud_config_test_value().hashCode());
        result = prime * result + ((getCloud_config_prod_value() == null) ? 0 : getCloud_config_prod_value().hashCode());
        return result;
    }
}