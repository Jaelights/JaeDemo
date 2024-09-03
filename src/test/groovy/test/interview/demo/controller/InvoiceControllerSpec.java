package test.interview.demo.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import test.interview.demo.domain.Invoice;
import test.interview.demo.repository.BillingRecordRepo;
import test.interview.demo.repository.InvoiceRepo;
import test.interview.demo.repository.UserRepo;

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
    private UserRepo userRepo;

    @MockBean
    private BillingRecordRepo billingRecordRepo;

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
    public void gettingNonExistentRecordThrows404() throws Exception {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111119999");
        when(invoiceRepo.getById(any())).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/invoice/{id}", id))
                .andReturn();

        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

}
