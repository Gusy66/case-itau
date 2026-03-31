package br.com.itau.caseitau.renegotiation.repository;

import br.com.itau.caseitau.renegotiation.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
}
