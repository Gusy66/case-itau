package br.com.itau.caseitau.renegotiation.business;

import br.com.itau.caseitau.renegotiation.domain.Debt;
import br.com.itau.caseitau.renegotiation.domain.DebtStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimulationServiceTest {

    private final SimulationService simulationService = new SimulationService();

    @Test
    void shouldSimulateWithDiscountAndDownPaymentBonus() {
        Debt debt = buildDebt(new BigDecimal("1000.00"), new BigDecimal("200.00"));
        SimulationResult result = simulationService.simulate(debt, new BigDecimal("300.00"), 6);
        assertEquals(new BigDecimal("1200.00"), result.originalAmount());
        assertEquals(new BigDecimal("132.00"), result.discountAmount());
        assertEquals(new BigDecimal("1068.00"), result.negotiatedAmount());
        assertEquals(new BigDecimal("128.00"), result.installmentAmount());
    }

    @Test
    void shouldFailWhenInstallmentBelowMinimum() {
        Debt debt = buildDebt(new BigDecimal("200.00"), new BigDecimal("0.00"));
        assertThrows(BusinessException.class, () -> simulationService.simulate(debt, BigDecimal.ZERO, 24));
    }

    private Debt buildDebt(BigDecimal principal, BigDecimal fee) {
        Debt debt = new Debt();
        debt.setId(UUID.randomUUID());
        debt.setCustomerId(UUID.randomUUID());
        debt.setPrincipalAmount(principal);
        debt.setLateFeeAmount(fee);
        debt.setStatus(DebtStatus.OPEN);
        debt.setUpdatedAt(OffsetDateTime.now());
        return debt;
    }
}
