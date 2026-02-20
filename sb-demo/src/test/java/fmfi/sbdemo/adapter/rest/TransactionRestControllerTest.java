package fmfi.sbdemo.adapter.rest;

import fmfi.sbdemo.core.api.*;
import fmfi.sbdemo.core.api.dictionary.*;
import fmfi.sbdemo.core.api.dto.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

@WebMvcTest(TransactionRestController.class)
class TransactionRestControllerTest {

    @org.springframework.beans.factory.annotation.Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetLatestCurrentAccountTransactionsUseCase getTransactionsUseCase;

    @org.junit.jupiter.api.Test
    void getLatestCurrentAccountTransactions() throws Exception {
        // setup
        var iban = "SK8975000000000012345671";

        var resultDto = new CurrentAccountTransactionsDto(java.util.List.of(
            new CurrentAccountTransactionListItemDto(
                /* transactionId */ "667bb620-04c6-45cb-8746-80cf932c7a01",
                /* amount */ Money.eur(42),
                /* partnerName */ "Acme corp.",
                /* variableSymbol */ "123456",
                /* description */ "Anvil",
                /* accountBalance */ Money.eur(958),
                /* effectiveDate */ java.time.LocalDate.of(2022, 9, 24),
                /* status */ TransactionStatus.PROCESSED,
                /* direction */ TransactionDirection.DEBIT
            ),
            new CurrentAccountTransactionListItemDto(
                /* transactionId */ "1717c635-d19a-4399-b911-2d30578c065d",
                /* amount */ Money.eur(10),
                /* partnerName */ "Acme corp.",
                /* variableSymbol */ null,
                /* description */ "Cashback",
                /* accountBalance */ Money.eur(970),
                /* effectiveDate */ java.time.LocalDate.of(2022, 9, 25),
                /* status */ TransactionStatus.PROCESSED,
                /* direction */ TransactionDirection.CREDIT
            )
        ));

        when(getTransactionsUseCase.getLatestCurrentAccountTransactions(iban)).thenReturn(resultDto);

        String expectedResponseJson = """
            {
              "transactions": [
                {
                  "transactionId": "667bb620-04c6-45cb-8746-80cf932c7a01",
                  "amount": { "amount": 42, "currency": "EUR" },
                  "partnerName": "Acme corp.",
                  "variableSymbol": "123456",
                  "description": "Anvil",
                  "accountBalance": { "amount": 958, "currency": "EUR" },
                  "effectiveDate": "2022-09-24",
                  "status": "PROCESSED",
                  "direction": "DEBIT"
                },
                {
                  "transactionId": "1717c635-d19a-4399-b911-2d30578c065d",
                  "amount": { "amount": 10, "currency": "EUR" },
                  "partnerName": "Acme corp.",
                  "variableSymbol": null,
                  "description": "Cashback",
                  "accountBalance": { "amount": 970, "currency": "EUR" },
                  "effectiveDate": "2022-09-25",
                  "status": "PROCESSED",
                  "direction": "CREDIT"
                }
              ]
            }""";

        // run & verify
        this.mockMvc
            .perform(get("/api/current-accounts/{accountNumber}/transactions", iban))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResponseJson));
    }
}
