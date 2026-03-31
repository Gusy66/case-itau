package br.com.itau.caseitau.renegotiation.business;

import java.math.BigDecimal;

public record SimulationResult(
        BigDecimal originalAmount,
        BigDecimal discountAmount,
        BigDecimal negotiatedAmount,
        BigDecimal downPayment,
        Integer installmentCount,
        BigDecimal installmentAmount
) {
}
