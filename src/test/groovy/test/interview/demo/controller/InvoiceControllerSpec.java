package test.interview.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.domain.Invoice;
import test.interview.demo.repository.InvoiceRepo;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.jayway.jsonpath.JsonPath.read;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
public class InvoiceControllerSpec {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceRepo invoiceRepo;

    @MockBean
    private Invoice mockInvoice;

    @Test
    public void getInvoiceByIdReturnsProperID() throws Exception {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");

        when(mockInvoice.getId()).thenReturn(id);
        when(invoiceRepo.getById(any())).thenReturn(mockInvoice);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/invoice/{id}", id))
                .andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(id, UUID.fromString(read(content, "$.id")));
    }

    @Test
    public void removeDupesRemovesDupes() throws Exception {
        BillingRecord billingRecord1 = BillingRecord.builder().id(UUID.fromString("11111111-1111-1111-1111-111111111111")).build();
        BillingRecord billingRecord2 = BillingRecord.builder().id(UUID.fromString("11111111-1111-1111-1111-111111111112")).build();
        BillingRecord billingRecord3 = BillingRecord.builder().id(UUID.fromString("11111111-1111-1111-1111-111111111111")).build();


        List<BillingRecord> records = Arrays.asList(billingRecord1, billingRecord2, billingRecord3);

        List<BillingRecord> resultRecords = InvoiceController.removeDupes(records);
        Assertions.assertEquals(2, resultRecords.size());
    }
}
