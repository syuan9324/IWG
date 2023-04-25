package com.iisi.patrol.webGuard.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class IwgHostsDTO implements Serializable {

    private String password;
    private String username;
    private String hostname;
    private Integer port;
    private String mailRecevier;
    private String smsRecevier;
    private String createUser;
    private Instant createTime;
    private String updateUser;
    private Instant updateTime;
    private String targetFilename;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getMailRecevier() {
        return mailRecevier;
    }

    public void setMailRecevier(String mailRecevier) {
        this.mailRecevier = mailRecevier;
    }

    public String getSmsRecevier() {
        return smsRecevier;
    }

    public void setSmsRecevier(String smsRecevier) {
        this.smsRecevier = smsRecevier;
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
        if (!(o instanceof IwgHostsDTO)) return false;
        IwgHostsDTO that = (IwgHostsDTO) o;
        return  Objects.equals(password, that.password) && Objects.equals(username, that.username) && Objects.equals(hostname, that.hostname) && Objects.equals(port, that.port) && Objects.equals(mailRecevier, that.mailRecevier) && Objects.equals(smsRecevier, that.smsRecevier) && Objects.equals(createUser, that.createUser) && Objects.equals(createTime, that.createTime) && Objects.equals(updateUser, that.updateUser) && Objects.equals(updateTime, that.updateTime) && Objects.equals(targetFilename, that.targetFilename);
    }

    @Override
    public int hashCode() {
        return Objects.hash( password, username, hostname, port, mailRecevier, smsRecevier, createUser, createTime, updateUser, updateTime, targetFilename);
    }

    @Override
    public String toString() {
        return "IwgHostsDTO{" +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", hostname='" + hostname + '\'' +
                ", port=" + port +
                ", mailRecevier='" + mailRecevier + '\'' +
                ", smsRecevier='" + smsRecevier + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", targetFilename='" + targetFilename + '\'' +
                '}';
    }
}
