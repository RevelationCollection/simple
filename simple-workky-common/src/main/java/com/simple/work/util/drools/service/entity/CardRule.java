package com.simple.work.util.drools.service.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 规则表
 */
public class CardRule {

    private Long id;

    private String modelCode;

    private String ruleCode;

    private String ruleName;

    private String ruleDim;

    private String pRuleCode;

    private BigDecimal ruleWeight;

    private BigDecimal ruleScore;

    private BigDecimal hitScore;

    private String memo;

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

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode == null ? null : ruleCode.trim();
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public String getRuleDim() {
        return ruleDim;
    }

    public void setRuleDim(String ruleDim) {
        this.ruleDim = ruleDim == null ? null : ruleDim.trim();
    }

    public String getpRuleCode() {
        return pRuleCode;
    }

    public void setpRuleCode(String pRuleCode) {
        this.pRuleCode = pRuleCode == null ? null : pRuleCode.trim();
    }

    public BigDecimal getRuleWeight() {
        return ruleWeight;
    }

    public void setRuleWeight(BigDecimal ruleWeight) {
        this.ruleWeight = ruleWeight;
    }

    public BigDecimal getRuleScore() {
        return ruleScore;
    }

    public void setRuleScore(BigDecimal ruleScore) {
        this.ruleScore = ruleScore;
    }

    public BigDecimal getHitScore() {
        return hitScore;
    }

    public void setHitScore(BigDecimal hitScore) {
        this.hitScore = hitScore;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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

    @Override
    public String toString() {
        return "CardRule{" +
                "id=" + id +
                ", modelCode='" + modelCode + '\'' +
                ", ruleCode='" + ruleCode + '\'' +
                ", ruleName='" + ruleName + '\'' +
                ", ruleDim='" + ruleDim + '\'' +
                ", pRuleCode='" + pRuleCode + '\'' +
                ", ruleWeight=" + ruleWeight +
                ", ruleScore=" + ruleScore +
                ", hitScore=" + hitScore +
                ", memo='" + memo + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}