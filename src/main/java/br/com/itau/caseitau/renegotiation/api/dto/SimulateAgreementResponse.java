package br.com.itau.caseitau.renegotiation.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record SimulateAgreementResponse(
        UUID debtId,
        BigDecimal originalAmount,
        BigDecimal discountAmount,
        BigDecimal negotiatedAmount,
        BigDecimal downPayment,
        Integer installmentCount,
        BigDecimal installmentAmount
) {
}
