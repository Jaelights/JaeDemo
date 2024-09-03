package test.interview.demo.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.domain.Invoice;
import test.interview.demo.domain.User;
import test.interview.demo.repository.BillingRecordRepo;
import test.interview.demo.repository.InvoiceRepo;
import test.interview.demo.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
public class UserControllerSpec {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceRepo invoiceRepo;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private BillingRecordRepo billingRecordRepo;

    @MockBean
    private User mockUser;

    @Test
    public void gettingNonExistentUserThrows404ForInvoices() throws Exception {
        int id = 999;
        when(userRepo.getUserRecordByID(any())).thenReturn(null);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/{id}/invoices", id))
                .andReturn();

        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void gettingNonExistentUserThrows404ForBillingRecords() throws Exception {
        int id = 999;
        when(userRepo.getUserRecordByID(any())).thenReturn(null);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/{id}/billing_records", id))
                .andReturn();

        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void gettingInvoicesForUserReturnsCorrectInvoices() throws Exception {
        int id = 1;
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(Invoice.builder().id(UUID.randomUUID()).build());
        invoices.add(Invoice.builder().id(UUID.randomUUID()).build());
        invoices.add(Invoice.builder().id(UUID.randomUUID()).build());

        when(userRepo.getUserRecordByID(any())).thenReturn(mockUser);
        when(mockUser.getCustomerNumber()).thenReturn(id);
        when(invoiceRepo.getAllInvoicesForID(id)).thenReturn(invoices);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/{id}/invoices", id))
                .andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(invoices.size(), 3);
    }

    @Test
    public void gettingBillingForUserReturnsCorrectBillingRecords() throws Exception {
        int id = 1;
        List<BillingRecord> billingRecords = new ArrayList<>();
        billingRecords.add(BillingRecord.builder().id(UUID.randomUUID()).build());
        billingRecords.add(BillingRecord.builder().id(UUID.randomUUID()).build());
        billingRecords.add(BillingRecord.builder().id(UUID.randomUUID()).build());

        when(userRepo.getUserRecordByID(any())).thenReturn(mockUser);
        when(mockUser.getCustomerNumber()).thenReturn(id);
        when(billingRecordRepo.getAllBillingRecordsForID(id)).thenReturn(billingRecords);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/{id}/billing_records", id))
                .andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(billingRecords.size(), 3);
    }
}
