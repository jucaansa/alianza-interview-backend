package com.juan_andrade.alianza_interview.controller;

import com.juan_andrade.alianza_interview.dto.client.ClientDto;
import com.juan_andrade.alianza_interview.dto.client.ClientRequest;
import com.juan_andrade.alianza_interview.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.UUID;

@RestController
@RequestMapping("v1/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<Page<ClientDto>> findAll(Pageable pageable, @RequestParam(name = "search", required = false) String search) {
        return ResponseEntity.ok().body(clientService.findAll(pageable, search));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ClientDto> findByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok().body(clientService.findByUuid(uuid));
    }

    @GetMapping("/by-shared-key/{sharedKey}")
    public ResponseEntity<ClientDto> findBySharedKey(@PathVariable String sharedKey) {
        return ResponseEntity.ok().body(clientService.findBySharedKey(sharedKey));
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@Validated @RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok().body(clientService.create(clientRequest));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<ClientDto> update(
            @PathVariable UUID uuid,
            @RequestBody ClientRequest clientRequest
    ) {
        return ResponseEntity.ok().body(clientService.update(uuid, clientRequest));
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid) {
        clientService.delete(uuid);
    }

}
