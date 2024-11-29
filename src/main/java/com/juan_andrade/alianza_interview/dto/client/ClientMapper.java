package com.juan_andrade.alianza_interview.dto.client;

import com.juan_andrade.alianza_interview.entity.client.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ClientMapper {
    ClientDto toDto(Client client);
    Client toEntity(ClientRequest clientRequest);
    void update(ClientRequest clientRequest, @MappingTarget Client client);
}
