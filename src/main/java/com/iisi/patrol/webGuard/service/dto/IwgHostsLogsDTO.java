package com.iisi.patrol.webGuard.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class IwgHostsLogsDTO implements Serializable {

    private String hostname;
    private Integer port;
    private Instant triggerTime;
    private Instant finishTime;
    private String result;
    private String smsStatus;
    private String mailStatus;
    private String createUser;
    private Instant createTime;
    private String updateUser;
    private Instant updateTime;
    private String targetFilename;


    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Instant getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Instant triggerTime) {
        this.triggerTime = triggerTime;
    }

    public Instant getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }

    public String getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(String mailStatus) {
        this.mailStatus = mailStatus;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getTargetFilename() {
        return targetFilename;
    }

    public void setTargetFilename(String targetFilename) {
        this.targetFilename = targetFilename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IwgHostsLogsDTO)) return false;
        IwgHostsLogsDTO that = (IwgHostsLogsDTO) o;
        return  Objects.equals(hostname, that.hostname) && Objects.equals(port, that.port) && Objects.equals(triggerTime, that.triggerTime) && Objects.equals(finishTime, that.finishTime) && Objects.equals(result, that.result) && Objects.equals(smsStatus, that.smsStatus) && Objects.equals(mailStatus, that.mailStatus) && Objects.equals(createUser, that.createUser) && Objects.equals(createTime, that.createTime) && Objects.equals(updateUser, that.updateUser) && Objects.equals(updateTime, that.updateTime) && Objects.equals(targetFilename, that.targetFilename);
    }

    @Override
    public int hashCode() {
        return Objects.hash( hostname, port, triggerTime, finishTime, result, smsStatus, mailStatus, createUser, createTime, updateUser, updateTime, targetFilename);
    }

    @Override
    public String toString() {
        return "IwgHostsLogsDTO{" +
                ", hostname='" + hostname + '\'' +
                ", port=" + port +
                ", triggerTime=" + triggerTime +
                ", finishTime=" + finishTime +
                ", result='" + result + '\'' +
                ", smsStatus='" + smsStatus + '\'' +
                ", mailStatus='" + mailStatus + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", targetFilename='" + targetFilename + '\'' +
                '}';
    }
}
