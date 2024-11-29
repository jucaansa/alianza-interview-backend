package com.juan_andrade.alianza_interview.repository;

import com.juan_andrade.alianza_interview.entity.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client> {
    Optional<Client> findBySharedKey(String sharedKey);
    Boolean existsBySharedKey(String sharedKey);
}
