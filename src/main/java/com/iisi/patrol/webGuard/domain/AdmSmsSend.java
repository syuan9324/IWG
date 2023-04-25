package com.iisi.patrol.webGuard.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A AdmSmsSend.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "adm_sms_send")
public class AdmSmsSend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admSmsSendSeq")
    @SequenceGenerator(name = "admSmsSendSeq", sequenceName = "ADM_SMS_SEND_SEQ", allocationSize = 1)
    private Long id;

    /**
     * type: varchar(10)
     */
    @NotNull
    @Size(max = 10)
    @Schema(description = "type: varchar(10)", required = true)
    @Column(name = "sms_type", length = 10, nullable = false)
    private String smsType;

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
    @Column(name = "recever")
    private String recever;

    /**
     * type: nvarchar(30)
     */
    @Size(max = 30)
    @Schema(description = "type: nvarchar(30)")
    @Column(name = "supplier", length = 30)
    private String supplier;

    /**
     * type: nvarchar(1000)
     */
    @Size(max = 1000)
    @Schema(description = "type: nvarchar(1000)")
    @Column(name = "message", length = 1000)
    private String message;

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

    @Size(max = 20)
    @Schema(description = "type: varchar(20)", required = true)
    @Column(name = "create_user", length = 20)
    private String createUser;

    /**
     * type: datetime
     */

    @Schema(description = "type: datetime", required = true)
    @Column(name = "create_time")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdmSmsSend id(Long id) {
        this.id = id;
        return this;
    }

    public String getSmsType() {
        return this.smsType;
    }

    public AdmSmsSend smsType(String smsType) {
        this.smsType = smsType;
        return this;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public AdmSmsSend sourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSender() {
        return this.sender;
    }

    public AdmSmsSend sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecever() {
        return this.recever;
    }

    public AdmSmsSend recever(String recever) {
        this.recever = recever;
        return this;
    }

    public void setRecever(String recever) {
        this.recever = recever;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public AdmSmsSend supplier(String supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getMessage() {
        return this.message;
    }

    public AdmSmsSend message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getExpectSendTime() {
        return this.expectSendTime;
    }

    public AdmSmsSend expectSendTime(Instant expectSendTime) {
        this.expectSendTime = expectSendTime;
        return this;
    }

    public void setExpectSendTime(Instant expectSendTime) {
        this.expectSendTime = expectSendTime;
    }

    public Instant getRealSendTime() {
        return this.realSendTime;
    }

    public AdmSmsSend realSendTime(Instant realSendTime) {
        this.realSendTime = realSendTime;
        return this;
    }

    public void setRealSendTime(Instant realSendTime) {
        this.realSendTime = realSendTime;
    }

    public String getStatus() {
        return this.status;
    }

    public AdmSmsSend status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessMessage() {
        return this.processMessage;
    }

    public AdmSmsSend processMessage(String processMessage) {
        this.processMessage = processMessage;
        return this;
    }

    public void setProcessMessage(String processMessage) {
        this.processMessage = processMessage;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public AdmSmsSend createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public AdmSmsSend createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public AdmSmsSend updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public AdmSmsSend updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdmSmsSend)) {
            return false;
        }
        return id != null && id.equals(((AdmSmsSend) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdmSmsSend{" +
            "id=" + getId() +
            ", smsType='" + getSmsType() + "'" +
            ", sourceId='" + getSourceId() + "'" +
            ", sender='" + getSender() + "'" +
            ", recever='" + getRecever() + "'" +
            ", supplier='" + getSupplier() + "'" +
            ", message='" + getMessage() + "'" +
            ", expectSendTime='" + getExpectSendTime() + "'" +
            ", realSendTime='" + getRealSendTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", processMessage='" + getProcessMessage() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
