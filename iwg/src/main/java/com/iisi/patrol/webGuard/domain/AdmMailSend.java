package com.iisi.patrol.webGuard.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A AdmMailSend.
 */
@Entity
@Table(name = "adm_mail_send")
public class AdmMailSend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admMailSendSeq")
    @SequenceGenerator(name = "admMailSendSeq", sequenceName = "ADM_MAIL_SEND_SEQ", allocationSize = 1)
    private Long id;

    /**
     * type: varchar(10)
     */
    @Size(max = 10)
    @Schema(description = "type: varchar(10)", required = true)
    @Column(name = "mail_type", length = 10)
    private String mailType;

    /**
     * type: varchar(100)
     */
    @NotNull
    @Size(max = 100)
    @Schema(description = "type: varchar(100)", required = true)
    @Column(name = "source_id", length = 100, nullable = false)
    private String sourceId;

    /**
     * type: varchar(50)
     */
    @Size(max = 50)
    @Schema(description = "type: varchar(50)")
    @Column(name = "sender", length = 50)
    private String sender;

    /**
     * type: varchar(MAX)
     */
    @Schema(description = "type: varchar(MAX)")
    @Column(name = "receiver")
    private String receiver;

    /**
     * type: nvarchar(100)
     */
    @Size(max = 200)
    @Schema(description = "type: nvarchar(200)")
    @Column(name = "subject", length = 200)
    private String subject;

    /**
     * type: nvarchar(MAX)
     */
    @Schema(description = "type: nvarchar(MAX)")
    @Column(name = "content")
    private String content;

    /**
     * type: datetime
     */
    @Schema(description = "type: datetime")
    @Column(name = "expect_send_time")
    private Instant expectSendTime;

    /**
     * type: datetime
     */
    @Schema(description = "type: datetime")
    @Column(name = "real_send_time")
    private Instant realSendTime;

    /**
     * type: varchar(1)
     */
    @NotNull
    @Size(max = 1)
    @Schema(description = "type: varchar(1)", required = true)
    @Column(name = "status", length = 1, nullable = false)
    private String status;

    /**
     * type: nvarchar(MAX)
     */
    @Schema(description = "type: nvarchar(MAX)")
    @Column(name = "process_message")
    private String processMessage;

    /**
     * type: varchar(20)
     */
    @NotNull
    @Size(max = 20)
    @Schema(description = "type: varchar(20)", required = true)
    @Column(name = "create_user", length = 20, nullable = false)
    private String createUser;

    /**
     * type: datetime
     */
    @NotNull
    @Schema(description = "type: datetime", required = true)
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * type: varchar(20)
     */
    @Size(max = 20)
    @Schema(description = "type: varchar(20)")
    @Column(name = "update_user", length = 20)
    private String updateUser;

    /**
     * type: datetime
     */
    @Schema(description = "type: datetime")
    @Column(name = "update_time")
    private Instant updateTime;

    @Size(max = 20)
    @Schema(description = "type: varchar(500)")
    @Column(name = "file_path", length = 500)
    private String filePath;

    //表格欄位新增10/3加入
    @Column(name = "is_html")
    private Boolean isHtml;

    public Boolean getIsHtml() {
        return isHtml;
    }

    public void setIsHtml(Boolean ishtml) {
        isHtml = ishtml;
    }

    public void setHtml(Boolean html) {
        isHtml = html;
    }

    public AdmMailSend isHtml(Boolean isHtml) {
        this.isHtml = isHtml;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdmMailSend id(Long id) {
        this.id = id;
        return this;
    }

    public String getMailType() {
        return this.mailType;
    }

    public AdmMailSend mailType(String mailType) {
        this.mailType = mailType;
        return this;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public AdmMailSend sourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSender() {
        return this.sender;
    }

    public AdmMailSend sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public AdmMailSend receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return this.subject;
    }

    public AdmMailSend subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return this.content;
    }

    public AdmMailSend content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getExpectSendTime() {
        return this.expectSendTime;
    }

    public AdmMailSend expectSendTime(Instant expectSendTime) {
        this.expectSendTime = expectSendTime;
        return this;
    }

    public void setExpectSendTime(Instant expectSendTime) {
        this.expectSendTime = expectSendTime;
    }

    public Instant getRealSendTime() {
        return this.realSendTime;
    }

    public AdmMailSend realSendTime(Instant realSendTime) {
        this.realSendTime = realSendTime;
        return this;
    }

    public void setRealSendTime(Instant realSendTime) {
        this.realSendTime = realSendTime;
    }

    public String getStatus() {
        return this.status;
    }

    public AdmMailSend status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessMessage() {
        return this.processMessage;
    }

    public AdmMailSend processMessage(String processMessage) {
        this.processMessage = processMessage;
        return this;
    }

    public void setProcessMessage(String processMessage) {
        this.processMessage = processMessage;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public AdmMailSend createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public AdmMailSend createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public AdmMailSend updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public AdmMailSend updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdmMailSend)) {
            return false;
        }
        return id != null && id.equals(((AdmMailSend) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "AdmMailSend{" +
                "id=" + id +
                ", mailType='" + mailType + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", expectSendTime=" + expectSendTime +
                ", realSendTime=" + realSendTime +
                ", status='" + status + '\'' +
                ", processMessage='" + processMessage + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", filePath='" + filePath + '\'' +
                ", isHtml=" + isHtml +
                '}';
    }
}
