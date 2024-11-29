package com.juan_andrade.alianza_interview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
abstract public class BaseEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private final UUID uuid = UUID.randomUUID();

    @Version
    private Long version;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_modified_at")
    @LastModifiedDate
    private LocalDateTime LastModifiedAt;

    public BaseEntity() {}

    public BaseEntity(Long version, LocalDateTime lastModifiedAt) {
        this.version = version;
        LastModifiedAt = lastModifiedAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return LastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        LastModifiedAt = lastModifiedAt;
    }
}
