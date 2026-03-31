package br.com.itau.caseitau.renegotiation.integration;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AgreementCreatedEvent(
        UUID agreementId,
        UUID debtId,
        UUID customerId,
        BigDecimal totalAmount,
        BigDecimal downPayment,
        Integer installmentCount,
        BigDecimal installmentAmount,
        OffsetDateTime createdAt
) {
}
