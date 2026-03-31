package br.com.itau.caseitau.renegotiation.api;

import br.com.itau.caseitau.renegotiation.api.dto.AgreementResponse;
import br.com.itau.caseitau.renegotiation.api.dto.CreateAgreementRequest;
import br.com.itau.caseitau.renegotiation.api.dto.SimulateAgreementRequest;
import br.com.itau.caseitau.renegotiation.api.dto.SimulateAgreementResponse;
import br.com.itau.caseitau.renegotiation.business.AgreementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AgreementController {

    private final AgreementService agreementService;

    public AgreementController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @PostMapping("/debts/{debtId}/simulate")
    public SimulateAgreementResponse simulate(
            @PathVariable UUID debtId,
            @RequestBody @Valid SimulateAgreementRequest request
    ) {
        return agreementService.simulate(debtId, request);
    }

    @PostMapping("/agreements")
    @ResponseStatus(HttpStatus.CREATED)
    public AgreementResponse createAgreement(@RequestBody @Valid CreateAgreementRequest request) {
        return agreementService.createAgreement(request);
    }

    @GetMapping("/agreements/{agreementId}")
    public AgreementResponse findAgreement(@PathVariable UUID agreementId) {
        return agreementService.findAgreement(agreementId);
    }
}
