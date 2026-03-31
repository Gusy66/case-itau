package br.com.itau.caseitau.renegotiation.repository;

import br.com.itau.caseitau.renegotiation.domain.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DebtRepository extends JpaRepository<Debt, UUID> {
}
