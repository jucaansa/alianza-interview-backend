package com.juan_andrade.alianza_interview.service.impl;

import com.juan_andrade.alianza_interview.dto.client.ClientDto;
import com.juan_andrade.alianza_interview.dto.client.ClientMapper;
import com.juan_andrade.alianza_interview.dto.client.ClientRequest;
import com.juan_andrade.alianza_interview.entity.client.Client;
import com.juan_andrade.alianza_interview.repository.ClientRepository;
import com.juan_andrade.alianza_interview.service.ClientService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    private final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public Page<ClientDto> findAll(Pageable pageable, String search) {
        Specification<Client> spec = getSpecs(search);
        return clientRepository.findAll(spec, pageable).map(clientMapper::toDto);
    }

    @Override
    public ClientDto findByUuid(UUID uuid) {
        Client client = clientRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found with uuid: " + uuid));
        return clientMapper.toDto(client);
    }

    @Override
    public ClientDto findBySharedKey(String sharedKey) {
        Client client = clientRepository.findBySharedKey(sharedKey).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found with shared key: " + sharedKey));
        return clientMapper.toDto(client);
    }

    @Transactional
    @Override
    public ClientDto create(ClientRequest clientRequest) {
        try {
            Client client = clientMapper.toEntity(clientRequest);
            return clientMapper.toDto(clientRepository.save(client));
        } catch (Exception e) {
            logger.error("Error during save new client -> {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Transactional
    @Override
    public ClientDto update(UUID uuid, ClientRequest clientRequest) {
        Client client = clientRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found with uuid: " + uuid));
        clientMapper.update(clientRequest, client);
        return clientMapper.toDto(clientRepository.save(client));
    }

    @Transactional
    @Override
    public void delete(UUID uuid) {
        Client client = clientRepository.findById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found with uuid: " + uuid));
        clientRepository.delete(client);
    }

    private Specification<Client> getSpecs(String search) {
        Specification<Client> spec = Specification.where(null);
        if (search == null || search.isBlank()) return spec;

        List<String> validFields = Arrays.stream(Client.class.getDeclaredFields()).map(Field::getName).toList();
        List<String> params = Arrays.stream(search.split(" AND ")).map(String::trim).toList();

        for (String condition : params) {
            List<String> data = Arrays.stream(condition.split(":")).map(String::trim).toList();
            if (data.size() == 2) {
                String key = data.get(0);
                String value = data.get(1);
                if (validFields.contains(key)) {
                    Specification<Client> newSpec = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(key), value);
                    spec = spec.and(newSpec);
                }
            }
        }
        
        return spec;
    }
}
