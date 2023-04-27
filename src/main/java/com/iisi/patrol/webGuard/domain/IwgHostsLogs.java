package com.iisi.patrol.webGuard.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Entity
public class IwgHostsLogs implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 25)
    @Column(name = "hostname", length = 25, nullable = false)
    private String hostname;

    @NotNull
    @Size(max = 4)
    @Column(name = "port", length = 4, nullable = false)
    private Integer port;

    @Column(name = "trigger_time", nullable = false)
    private Instant triggerTime;

    @Column(name = "finish_time", nullable = false)
    private Instant finishTime;

    @Size(max = 2)
    @Column(name = "result", length = 2, nullable = true)
    private String result;

    @Size(max = 500)
    @Column(name = "sms_status", length = 500, nullable = true)
    private String smsStatus;

    @Size(max = 500)
    @Column(name = "mail_status", length = 500, nullable = true)
    private String mailStatus;

    @Size(max = 25)
    @Column(name = "create_user", length = 25, nullable = true)
    private String createUser;

    @Column(name = "create_time", nullable = true)
    private Instant createTime;

    @Size(max = 25)
    @Column(name = "update_user", length = 25, nullable = true)
    private String updateUser;

    @Column(name = "update_time", nullable = true)
    private Instant updateTime;

    @Size(max = 50)
    @Column(name = "target_filename", length = 50, nullable = true)
    private String targetFilename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
