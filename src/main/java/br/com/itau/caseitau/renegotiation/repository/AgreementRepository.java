package br.com.itau.caseitau.renegotiation.repository;

import br.com.itau.caseitau.renegotiation.domain.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgreementRepository extends JpaRepository<Agreement, UUID> {
}
