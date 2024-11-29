package com.juan_andrade.alianza_interview.service;

import com.juan_andrade.alianza_interview.dto.client.ClientDto;
import com.juan_andrade.alianza_interview.dto.client.ClientRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClientService {
    Page<ClientDto> findAll(Pageable pageable, String search);
    ClientDto findByUuid(UUID uuid);
    ClientDto findBySharedKey(String sharedKey);
    ClientDto create(ClientRequest clientRequest);
    ClientDto update(UUID uuid, ClientRequest clientRequest);
    void delete(UUID uuid);
}
