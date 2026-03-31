package br.com.itau.caseitau.renegotiation.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AgreementResponse(
        UUID agreementId,
        UUID debtId,
        UUID customerId,
        BigDecimal totalAmount,
        BigDecimal downPayment,
        Integer installmentCount,
        BigDecimal installmentAmount,
        String status,
        OffsetDateTime createdAt
) {
}
