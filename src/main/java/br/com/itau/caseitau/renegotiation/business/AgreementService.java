package br.com.itau.caseitau.renegotiation.business;

import br.com.itau.caseitau.renegotiation.api.dto.AgreementResponse;
import br.com.itau.caseitau.renegotiation.api.dto.CreateAgreementRequest;
import br.com.itau.caseitau.renegotiation.api.dto.SimulateAgreementRequest;
import br.com.itau.caseitau.renegotiation.api.dto.SimulateAgreementResponse;
import br.com.itau.caseitau.renegotiation.domain.Agreement;
import br.com.itau.caseitau.renegotiation.domain.AgreementStatus;
import br.com.itau.caseitau.renegotiation.domain.Debt;
import br.com.itau.caseitau.renegotiation.domain.DebtStatus;
import br.com.itau.caseitau.renegotiation.integration.AgreementCreatedEvent;
import br.com.itau.caseitau.renegotiation.integration.OutboxPublisher;
import br.com.itau.caseitau.renegotiation.repository.AgreementRepository;
import br.com.itau.caseitau.renegotiation.repository.DebtRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class AgreementService {

    private final DebtRepository debtRepository;
    private final AgreementRepository agreementRepository;
    private final SimulationService simulationService;
    private final OutboxPublisher outboxPublisher;

    public AgreementService(
            DebtRepository debtRepository,
            AgreementRepository agreementRepository,
            SimulationService simulationService,
            OutboxPublisher outboxPublisher
    ) {
        this.debtRepository = debtRepository;
        this.agreementRepository = agreementRepository;
        this.simulationService = simulationService;
        this.outboxPublisher = outboxPublisher;
    }

    @Transactional(readOnly = true)
    public SimulateAgreementResponse simulate(UUID debtId, SimulateAgreementRequest request) {
        Debt debt = loadOpenDebt(debtId);
        SimulationResult result = simulationService.simulate(debt, request.downPayment(), request.installmentCount());
        return new SimulateAgreementResponse(
                debt.getId(),
                result.originalAmount(),
                result.discountAmount(),
                result.negotiatedAmount(),
                result.downPayment(),
                result.installmentCount(),
                result.installmentAmount()
        );
    }

    @Transactional
    public AgreementResponse createAgreement(CreateAgreementRequest request) {
        Debt debt = loadOpenDebt(request.debtId());
        SimulationResult result = simulationService.simulate(debt, request.downPayment(), request.installmentCount());
        Agreement agreement = new Agreement();
        agreement.setId(UUID.randomUUID());
        agreement.setDebtId(debt.getId());
        agreement.setCustomerId(debt.getCustomerId());
        agreement.setTotalAmount(result.negotiatedAmount());
        agreement.setDownPayment(result.downPayment());
        agreement.setInstallmentCount(result.installmentCount());
        agreement.setInstallmentAmount(result.installmentAmount());
        agreement.setStatus(AgreementStatus.CREATED);
        agreement.setCreatedAt(OffsetDateTime.now());
        agreementRepository.save(agreement);

        debt.setStatus(DebtStatus.RENEGOTIATED);
        debt.setUpdatedAt(OffsetDateTime.now());
        debtRepository.save(debt);

        AgreementCreatedEvent event = new AgreementCreatedEvent(
                agreement.getId(),
                agreement.getDebtId(),
                agreement.getCustomerId(),
                agreement.getTotalAmount(),
                agreement.getDownPayment(),
                agreement.getInstallmentCount(),
                agreement.getInstallmentAmount(),
                agreement.getCreatedAt()
        );
        outboxPublisher.publish("Agreement", agreement.getId(), "AgreementCreated", event);
        return toResponse(agreement);
    }

    @Transactional(readOnly = true)
    public AgreementResponse findAgreement(UUID agreementId) {
        Agreement agreement = agreementRepository.findById(agreementId)
                .orElseThrow(() -> new BusinessException("Acordo nao encontrado"));
        return toResponse(agreement);
    }

    private Debt loadOpenDebt(UUID debtId) {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new BusinessException("Divida nao encontrada"));
        if (debt.getStatus() != DebtStatus.OPEN) {
            throw new BusinessException("Divida indisponivel para renegociacao");
        }
        return debt;
    }

    private AgreementResponse toResponse(Agreement agreement) {
        return new AgreementResponse(
                agreement.getId(),
                agreement.getDebtId(),
                agreement.getCustomerId(),
                agreement.getTotalAmount(),
                agreement.getDownPayment(),
                agreement.getInstallmentCount(),
                agreement.getInstallmentAmount(),
                agreement.getStatus().name(),
                agreement.getCreatedAt()
        );
    }
}
