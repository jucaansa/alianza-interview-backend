package com.juan_andrade.alianza_interview.service;

import com.juan_andrade.alianza_interview.dto.client.ClientDto;
import com.juan_andrade.alianza_interview.dto.client.ClientMapper;
import com.juan_andrade.alianza_interview.dto.client.ClientRequest;
import com.juan_andrade.alianza_interview.entity.client.Client;
import com.juan_andrade.alianza_interview.repository.ClientRepository;
import com.juan_andrade.alianza_interview.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    private ClientServiceImpl clientServiceImpl;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        this.clientServiceImpl = new ClientServiceImpl(clientRepository, clientMapper);
    }

    @Test
    void findAllSearchNull() {
        Mockito.when(clientRepository.findAll(Mockito.<Specification<Client>>any(), Mockito.<Pageable>any())).thenReturn(new PageImpl<>(List.of(new Client())));
        Mockito.when(clientMapper.toDto(Mockito.any())).thenReturn(new ClientDto());
        Page<ClientDto> clients = clientServiceImpl.findAll(Pageable.unpaged(), null);

        Assertions.assertFalse(clients.isEmpty());
    }

    @Test
    void findAllSearchBlank() {
        Mockito.when(clientRepository.findAll(Mockito.<Specification<Client>>any(), Mockito.<Pageable>any())).thenReturn(new PageImpl<>(List.of(new Client())));
        Mockito.when(clientMapper.toDto(Mockito.any())).thenReturn(new ClientDto());
        Page<ClientDto> clients = clientServiceImpl.findAll(Pageable.unpaged(), "");

        Assertions.assertFalse(clients.isEmpty());
    }

    @Test
    void findAllSearchHappyPath() {
        Mockito.when(clientRepository.findAll(Mockito.<Specification<Client>>any(), Mockito.<Pageable>any())).thenReturn(new PageImpl<>(List.of(new Client())));
        Mockito.when(clientMapper.toDto(Mockito.any())).thenReturn(new ClientDto());
        Page<ClientDto> clients = clientServiceImpl.findAll(Pageable.unpaged(), "sharedKey:SK001 AND name:test");

        Assertions.assertFalse(clients.isEmpty());
    }

    @Test
    void findAllBadSearch() {
        Mockito.when(clientRepository.findAll(Mockito.<Specification<Client>>any(), Mockito.<Pageable>any())).thenReturn(new PageImpl<>(List.of()));
        Page<ClientDto> clients = clientServiceImpl.findAll(Pageable.unpaged(), "sharedKey:SK001:test AND nameTest:test");

        Assertions.assertTrue(clients.isEmpty());
    }

    @Test
    void findByUuidHappyPath() {
        Client mockClient = new Client();
        mockClient.setSharedKey("SK001");
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(mockClient));

        ClientDto mockClientDto = new ClientDto();
        mockClientDto.setSharedKey("SK001");
        Mockito.when(clientMapper.toDto(Mockito.any())).thenReturn(mockClientDto);

        ClientDto dbClient = clientServiceImpl.findByUuid(UUID.randomUUID());
        Assertions.assertSame(dbClient.getSharedKey(), mockClient.getSharedKey());
    }

    @Test
    void findByUuidNotFound() {
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class, () -> clientServiceImpl.findByUuid(UUID.randomUUID()));
    }

    @Test
    void findBySharedKeyHappyPath() {
        Client mockClient = new Client();
        mockClient.setSharedKey("SK002");
        Mockito.when(clientRepository.findBySharedKey(Mockito.any())).thenReturn(Optional.of(mockClient));

        ClientDto mockClientDto = new ClientDto();
        mockClientDto.setSharedKey("SK002");
        Mockito.when(clientMapper.toDto(Mockito.any())).thenReturn(mockClientDto);

        ClientDto dbClient = clientServiceImpl.findBySharedKey("SK002");
        Assertions.assertSame(dbClient.getSharedKey(), mockClient.getSharedKey());
    }

    @Test
    void findBySharedKeyNotFound() {
        Mockito.when(clientRepository.findBySharedKey(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class, () -> clientServiceImpl.findBySharedKey("test"));
    }

    @Test
    void createHappyPath() {
        ClientDto mockClientDto = new ClientDto();
        mockClientDto.setSharedKey("SK003");

        Mockito.when(clientMapper.toEntity(Mockito.any())).thenReturn(new Client());
        Mockito.when(clientMapper.toDto(Mockito.any())).thenReturn(mockClientDto);
        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(new Client());

        ClientDto dbClient = clientServiceImpl.create(new ClientRequest());
        Assertions.assertNotNull(dbClient.getSharedKey());
    }

    @Test
    void createWithThrowException() {
        Mockito.when(clientMapper.toEntity(Mockito.any())).thenReturn(new Client());
        Mockito.when(clientRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);
        Assertions.assertThrows(ResponseStatusException.class, () -> clientServiceImpl.create(new ClientRequest()));
    }

    @Test
    void updateHappyPath() {
        ClientDto mockClientDto = new ClientDto();
        mockClientDto.setSharedKey("SK003");

        Mockito.when(clientMapper.toDto(Mockito.any())).thenReturn(mockClientDto);
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(new Client()));
        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(new Client());

        ClientDto dbClient = clientServiceImpl.update(UUID.randomUUID(), new ClientRequest());
        Assertions.assertNotNull(dbClient.getSharedKey());
    }

    @Test
    void updateNotFound() {
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class, () -> clientServiceImpl.update(UUID.randomUUID(), new ClientRequest()));
    }


}
