package com.juan_andrade.alianza_interview.dto.client;

import com.juan_andrade.alianza_interview.entity.enums.ClientType;
import jakarta.validation.constraints.NotEmpty;

public class ClientRequest {
    @NotEmpty(message = "shared_key must be not null or blank")
    private String sharedKey;
    @NotEmpty(message = "name must be not null or blank")
    private String name;
    private ClientType type;
    private String email;
    private String phone;

    public ClientRequest() {}

    public ClientRequest(String email, String name, String phone, String sharedKey, ClientType type) {
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
