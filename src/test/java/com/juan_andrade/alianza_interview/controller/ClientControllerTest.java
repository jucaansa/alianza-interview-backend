package com.juan_andrade.alianza_interview.controller;

import com.juan_andrade.alianza_interview.dto.client.ClientDto;
import com.juan_andrade.alianza_interview.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;
import java.util.UUID;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @Test
    void findAll200() throws Exception {
        ClientDto mockClient = new ClientDto();
        mockClient.setSharedKey("SH3232");
        Mockito.when(clientService.findAll(Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(List.of(mockClient)));
        mockMvc.perform(
                        get("/v1/clients").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    void findByUuid200() throws Exception {
        ClientDto mockClient = new ClientDto();
        mockClient.setSharedKey("SH3232");
        Mockito.when(clientService.findByUuid(Mockito.any())).thenReturn(mockClient);
        mockMvc.perform(
                        get("/v1/clients/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.shared_key").value(mockClient.getSharedKey()));
    }

    @Test
    void findBySharedKey200() throws Exception {
        ClientDto mockClient = new ClientDto();
        mockClient.setSharedKey("SH3232");
        Mockito.when(clientService.findBySharedKey(Mockito.any())).thenReturn(mockClient);
        mockMvc.perform(
                        get("/v1/clients/by-shared-key/" + "SH3232").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.shared_key").value(mockClient.getSharedKey()));
    }

    @Test
    void create200() throws Exception {
        ClientDto mockClient = new ClientDto();
        mockClient.setSharedKey("SH3232");

        Mockito.when(clientService.create(Mockito.any())).thenReturn(mockClient);
        mockMvc.perform(
                        post("/v1/clients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"shared_key\":\"SH3232\",\"name\":\"test\",\"type\":\"BASIC_CLIENT\"}")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.shared_key").value(mockClient.getSharedKey()));
    }

    @Test
    void create400EmptyAndNullParams() throws Exception {
        mockMvc.perform(
                        post("/v1/clients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"shared_key\":\"\",\"name\":null,\"type\":\"BASIC_CLIENT\"}")
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void create400SharedKeyExists() throws Exception {
        Mockito.when(clientService.create(Mockito.any())).thenThrow(new DataIntegrityViolationException("bad data"));
        mockMvc.perform(
                        post("/v1/clients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"shared_key\":\"SH3232\",\"name\":\"test\",\"type\":\"BASIC_CLIENT\"}")
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update200() throws Exception {
        ClientDto mockClient = new ClientDto();
        mockClient.setSharedKey("SH3232");

        Mockito.when(clientService.update(Mockito.any(), Mockito.any())).thenReturn(mockClient);
        mockMvc.perform(
                        patch("/v1/clients/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"shared_key\":\"SH3232\",\"name\":\"test\",\"type\":\"BASIC_CLIENT\"}")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.shared_key").value(mockClient.getSharedKey()));
    }

    @Test
    void delete200() throws Exception {
        doNothing().when(clientService).delete(Mockito.any());
        mockMvc.perform(
                        delete("/v1/clients/" + UUID.randomUUID()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(clientService).delete(Mockito.any());
        mockMvc.perform(
                        delete("/v1/clients/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

}
