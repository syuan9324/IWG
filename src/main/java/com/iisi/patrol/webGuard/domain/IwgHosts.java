package com.iisi.patrol.webGuard.domain;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

public class IwgHosts implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 25)
    @Column(name = "password", length = 25, nullable = false)
    private String password;

    @NotNull
    @Size(max = 25)
    @Column(name = "username", length = 25, nullable = false)
    private String username;

    @NotNull
    @Size(max = 25)
    @Column(name = "hostname", length = 25, nullable = false)
    private String hostname;

    @NotNull
    @Size(max = 4)
    @Column(name = "port", length = 4, nullable = false)
    private Integer port;

    @Size(max = 500)
    @Column(name = "mail_recevier", length = 500, nullable = true)
    private String mailRecevier;

    @Size(max = 500)
    @Column(name = "sms_recevier", length = 500, nullable = true)
    private String smsRecevier;

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
}
