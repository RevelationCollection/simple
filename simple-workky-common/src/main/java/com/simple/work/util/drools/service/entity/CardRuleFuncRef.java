package com.simple.work.util.drools.service.entity;

import java.util.Date;

/**
 * 规则引用函数表
 */
public class CardRuleFuncRef {

    private Long id;

    private String modelCode;

    private String funcRefCode;

    private String ruleCode;

    private String funcBaseCode;

    private String expressRealParams;

    private String status;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode == null ? null : modelCode.trim();
    }

    public String getFuncRefCode() {
        return funcRefCode;
    }

    public void setFuncRefCode(String funcRefCode) {
        this.funcRefCode = funcRefCode == null ? null : funcRefCode.trim();
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode == null ? null : ruleCode.trim();
    }

    public String getFuncBaseCode() {
        return funcBaseCode;
    }

    public void setFuncBaseCode(String funcBaseCode) {
        this.funcBaseCode = funcBaseCode == null ? null : funcBaseCode.trim();
    }

    public String getExpressRealParams() {
        return expressRealParams;
    }

    public void setExpressRealParams(String expressRealParams) {
        this.expressRealParams = expressRealParams == null ? null : expressRealParams.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
}