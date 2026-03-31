package br.com.itau.caseitau.renegotiation.business;

import br.com.itau.caseitau.renegotiation.domain.Debt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class SimulationService {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private static final BigDecimal MIN_INSTALLMENT_VALUE = new BigDecimal("50.00");

    public SimulationResult simulate(Debt debt, BigDecimal downPayment, Integer installmentCount) {
        validateInput(downPayment, installmentCount);
        BigDecimal originalAmount = debt.getPrincipalAmount().add(debt.getLateFeeAmount());
        BigDecimal discountPercent = resolveDiscountPercent(installmentCount, downPayment, originalAmount);
        BigDecimal discountAmount = originalAmount.multiply(discountPercent).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
        BigDecimal negotiatedAmount = originalAmount.subtract(discountAmount);
        if (downPayment.compareTo(negotiatedAmount) > 0) {
            throw new BusinessException("Entrada nao pode ser maior do que o valor negociado");
        }
        BigDecimal financedAmount = negotiatedAmount.subtract(downPayment);
        BigDecimal installmentAmount = financedAmount.divide(BigDecimal.valueOf(installmentCount), 2, RoundingMode.HALF_UP);
        if (installmentAmount.compareTo(MIN_INSTALLMENT_VALUE) < 0) {
            throw new BusinessException("Valor minimo de parcela e 50.00");
        }
        return new SimulationResult(
                originalAmount,
                discountAmount,
                negotiatedAmount,
                downPayment,
                installmentCount,
                installmentAmount
        );
    }

    private void validateInput(BigDecimal downPayment, Integer installmentCount) {
        if (downPayment.signum() < 0) {
            throw new BusinessException("Entrada nao pode ser negativa");
        }
        if (installmentCount < 1 || installmentCount > 24) {
            throw new BusinessException("Quantidade de parcelas deve estar entre 1 e 24");
        }
    }

    private BigDecimal resolveDiscountPercent(Integer installmentCount, BigDecimal downPayment, BigDecimal originalAmount) {
        BigDecimal basePercent;
        if (installmentCount == 1) {
            basePercent = new BigDecimal("12");
        } else if (installmentCount <= 6) {
            basePercent = new BigDecimal("8");
        } else if (installmentCount <= 12) {
            basePercent = new BigDecimal("4");
        } else {
            basePercent = BigDecimal.ZERO;
        }
        BigDecimal downPaymentPercent = downPayment.multiply(ONE_HUNDRED).divide(originalAmount, 2, RoundingMode.HALF_UP);
        if (downPaymentPercent.compareTo(new BigDecimal("20")) >= 0) {
            basePercent = basePercent.add(new BigDecimal("3"));
        }
        return basePercent;
    }
}
