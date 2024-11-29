package com.juan_andrade.alianza_interview.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseDto {
    private UUID uuid;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public BaseDto() {}

    public BaseDto(LocalDateTime createdAt, LocalDateTime lastModifiedAt, UUID uuid, Long version) {
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.uuid = uuid;
        this.version = version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
