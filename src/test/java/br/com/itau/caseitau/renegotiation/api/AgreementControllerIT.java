package br.com.itau.caseitau.renegotiation.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AgreementControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldSimulateDebtSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1/debts/11111111-1111-1111-1111-111111111111/simulate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "downPayment": 300.00,
                                  "installmentCount": 6
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.debtId").value("11111111-1111-1111-1111-111111111111"))
                .andExpect(jsonPath("$.negotiatedAmount").value(2594.4));
    }
}
