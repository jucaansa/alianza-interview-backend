package com.juan_andrade.alianza_interview.entity.client;

import com.juan_andrade.alianza_interview.entity.BaseEntity;
import com.juan_andrade.alianza_interview.entity.enums.ClientType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
@DynamicUpdate
public class Client extends BaseEntity {

    @Column(name = "shared_key", nullable = false, unique = true)
    private String sharedKey;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType type;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    public Client() {}

    public Client(Long version, LocalDateTime lastModifiedAt, String email, String name, String phone, String sharedKey, ClientType type) {
        super(version, lastModifiedAt);
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.sharedKey = sharedKey;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSharedKey() {
        return sharedKey;
    }

    public void setSharedKey(String sharedKey) {
        this.sharedKey = sharedKey;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }
}
