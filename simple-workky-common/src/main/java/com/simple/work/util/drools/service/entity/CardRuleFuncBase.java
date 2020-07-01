package com.simple.work.util.drools.service.entity;

import java.util.Date;

/**
 * 规则基础函数表
 */
public class CardRuleFuncBase {

    private Long id;

    private String funcBaseCode;

    private String funcBaseName;

    private String cnExpress;

    private String enExpress;

    private String expressFormParams;

    private String funcInfo;

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

    public String getFuncBaseCode() {
        return funcBaseCode;
    }

    public void setFuncBaseCode(String funcBaseCode) {
        this.funcBaseCode = funcBaseCode == null ? null : funcBaseCode.trim();
    }

    public String getFuncBaseName() {
        return funcBaseName;
    }

    public void setFuncBaseName(String funcBaseName) {
        this.funcBaseName = funcBaseName == null ? null : funcBaseName.trim();
    }

    public String getCnExpress() {
        return cnExpress;
    }

    public void setCnExpress(String cnExpress) {
        this.cnExpress = cnExpress == null ? null : cnExpress.trim();
    }

    public String getEnExpress() {
        return enExpress;
    }

    public void setEnExpress(String enExpress) {
        this.enExpress = enExpress == null ? null : enExpress.trim();
    }

    public String getExpressFormParams() {
        return expressFormParams;
    }

    public void setExpressFormParams(String expressFormParams) {
        this.expressFormParams = expressFormParams == null ? null : expressFormParams.trim();
    }

    public String getFuncInfo() {
        return funcInfo;
    }

    public void setFuncInfo(String funcInfo) {
        this.funcInfo = funcInfo == null ? null : funcInfo.trim();
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
        return "CardRuleFuncBase{" +
                "id=" + id +
                ", funcBaseCode='" + funcBaseCode + '\'' +
                ", funcBaseName='" + funcBaseName + '\'' +
                ", cnExpress='" + cnExpress + '\'' +
                ", enExpress='" + enExpress + '\'' +
                ", expressFormParams='" + expressFormParams + '\'' +
                ", funcInfo='" + funcInfo + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}