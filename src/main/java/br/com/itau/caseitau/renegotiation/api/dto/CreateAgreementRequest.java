package br.com.itau.caseitau.renegotiation.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAgreementRequest(
        @NotNull UUID debtId,
        @NotNull @DecimalMin(value = "0.00") BigDecimal downPayment,
        @NotNull @Min(1) @Max(24) Integer installmentCount
) {
}
